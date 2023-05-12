import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;


public class BusStopProcessor {
    private Map<String, Tap> tapOnMap = new HashMap<>();
    private List<Trip> tripList = new ArrayList<>();
    private FareProcessor fareProcessor;

    BusStopProcessor(FareProcessor fareProcessor) {
        this.fareProcessor = fareProcessor;
    }

    public List<Trip> processTaps(List<Tap> tapList) {
        processTapList(tapList);
        processRemainingTapOns();

        return tripList;
    }

    private void processTapList(List<Tap> tapList) {
        for (Tap tap: tapList) {
            if (tap.getTapStatus() == Tap.TapStatus.ON) {
                Tap tapOn = tapOnMap.get(tap.getPAN());

                if (tapOn != null) {
                    BigDecimal fare = fareProcessor.getFare(tapOn.getStopId(), BusStop.NO_TAP_OFF);
                    Trip trip = createTrip(tapOn, null, fare);

                    tripList.add(trip);
                }

                tapOnMap.put(tap.getPAN(), tap);
            } else {
                Tap tapOn = tapOnMap.get(tap.getPAN());

                if (tapOn == null) {
                    System.err.println("There is a tap off with no tap on for a trip at " +
                        tap.getStopId().getStopName() + " using PAN:" + tap.getPAN());

                    BigDecimal fare = fareProcessor.getFare(tap.getStopId(), BusStop.NO_TAP_ON);
                    Trip trip = createTrip(tap, null, fare);

                    tripList.add(trip);
                } else {
                    BigDecimal fare = fareProcessor.getFare(tapOn.getStopId(), tap.getStopId());
                    Trip trip = createTrip(tapOn, tap, fare);

                    tripList.add(trip);
                    tapOnMap.remove(tapOn.getPAN(), tapOn);
                    
                    validateTrip(tapOn, tap);
                }
            }
        }
    }

    private void validateTrip(Tap tapOn, Tap tapOff) {
        if (!tapOn.getBusId().equals(tapOff.getBusId())) {
            System.err.println("Mis-matched bus id (On:" + tapOn.getBusId() + " Off:" + tapOff.getBusId() + ") for a trip using PAN:" + tapOn.getPAN());
        }

        if (!tapOn.getCompanyId().equals(tapOff.getCompanyId())) {
            System.err.println("Mis-matched company id (On:" + tapOn.getCompanyId() + " Off:" + tapOff.getCompanyId() + ") for a trip using PAN:" + tapOn.getPAN());
        }
    }

    private void processRemainingTapOns() {
        List<Tap> tapList = new ArrayList<>(tapOnMap.values());
        Collections.sort(tapList, Comparator.comparing(Tap::getUTC));

        for (Tap tap : tapList) {
            BigDecimal fare = fareProcessor.getFare(tap.getStopId(), BusStop.NO_TAP_OFF);
            Trip trip = createTrip(tap, null, fare);

            tripList.add(trip);
        }
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
            tripStatus = (tapOn.getStopId() == tapOff.getStopId()) ?
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
