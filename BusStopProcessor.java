import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;


public class BusStopProcessor {
    private Map<String, Tap> tapOnMap = new HashMap<>();
    private List<Trip> tripList = new ArrayList<>();

    public void processTaps(List<Tap> tapList) {
        FareProcessor fareProcessor = new FareProcessor();

        for (Tap tap: tapList) {
            if (tap.getTapStatus() == Tap.TapStatus.ON) {
                Tap tapOn = tapOnMap.get(tap.getPAN());

                if (tapOn != null) {
                    BigDecimal fare = fareProcessor.getFare(tapOn.getStopId(), BusStop.NO_TAP_OFF);
                    Trip trip = createTrip(tapOn, null, fare);

                    tripList.add(trip);
                    System.out.println("Existing tap ON Full Fare: $" + fare);
                }

                tapOnMap.put(tap.getPAN(), tap);
            } else {
                Tap tapOn = tapOnMap.get(tap.getPAN());

                if (tapOn == null) {
                    BigDecimal fare = fareProcessor.getFare(tap.getStopId(), BusStop.NO_TAP_OFF);
                    Trip trip = createTrip(tap, null, fare);

                    tripList.add(trip);
                    System.out.println("No tap ON Full Fare: $" + fare);
                } else {
                    BigDecimal fare = fareProcessor.getFare(tapOn.getStopId(), tap.getStopId());
                    Trip trip = createTrip(tapOn, tap, fare);

                    tripList.add(trip);
                    System.out.println("On:" + tapOn.getStopId() + " Off:" + tap.getStopId());

                    tapOnMap.remove(tapOn.getPAN(), tapOn);
                }
            }
        }

        for (Tap tap : tapOnMap.values()) {
            BigDecimal fare = fareProcessor.getFare(tap.getStopId(), BusStop.NO_TAP_OFF);
            Trip trip = createTrip(tap, null, fare);

            tripList.add(trip);
            System.out.println("Left ON Full Fare: $" + fare);
        }

        //  Charge full fare for remaining ON taps from starting bus stop
        System.out.println(toString());
    }

    private Trip createTrip(Tap tapOn, Tap tapOff, BigDecimal fare) {
        Duration duration;
        BusStop offStop;
        Trip.Status tripStatus;
        Instant utcOff;

        if (tapOff == null) {
            utcOff = null;
            duration = Duration.ZERO;
            offStop = BusStop.NO_TAP_OFF;
            tripStatus = Trip.Status.INCOMPLETED;
        } else {
            utcOff = tapOff.getUTC();
            duration = Duration.between(tapOn.getUTC(), tapOff.getUTC());
            offStop = tapOff.getStopId();
            tripStatus = (tapOn.getBusId() == tapOff.getBusId()) ?
                Trip.Status.CANCELLED : Trip.Status.COMPLETED;
        }

        return new Trip(
            tapOn.getUTC(),
            utcOff,
            duration.toSeconds(),
            tapOn.getStopId(),
            offStop,
            fare,
            tapOn.getCompanyId(),
            tapOn.getBusId(),
            tapOn.getPAN(),
            tripStatus
        );
    }

    @Override
    public String toString() {
        String output = "Tap On Left in List:\n";
        for (Tap tap : tapOnMap.values()) {
            output += (tap.toString() + "\n");
        }

        return output;
    }
}
