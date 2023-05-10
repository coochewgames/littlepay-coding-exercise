import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;


public class BusStopProcessor {
    private Map<String, Tap> tapOnMap = new HashMap<>();

    public void processTaps(List<Tap> tapList) {
        FareProcessor fareProcessor = new FareProcessor();

        for (Tap tap: tapList) {
            if (tap.getTapStatus() == Tap.TapStatus.ON) {
                Tap tapOn = tapOnMap.get(tap.getPAN());

                if (tapOn != null) {
                    BigDecimal fare = fareProcessor.getFare(tapOn.getStopId(), FareProcessor.NO_TAP_OFF);
                    System.out.println("Already tap ON Full Fare: $" + fare);
                }

                tapOnMap.put(tap.getPAN(), tap);
            }
            else {
                Tap tapOn = tapOnMap.get(tap.getPAN());

                if (tapOn == null) {
                    BigDecimal fare = fareProcessor.getFare(tap.getStopId(), FareProcessor.NO_TAP_OFF);
                    System.out.println("No tap ON Full Fare: $" + fare);

                    continue;
                }

                //  Process stops
                System.out.println("On:" + tapOn.getStopId() + " Off:" + tap.getStopId());
                BigDecimal fare = fareProcessor.getFare(tapOn.getStopId(), tap.getStopId());
                System.out.println("Fare: $" + fare);

                tapOnMap.remove(tapOn.getPAN(), tapOn);
            }
        }

        for (Tap tap : tapOnMap.values()) {
            BigDecimal fare = fareProcessor.getFare(tap.getStopId(), FareProcessor.NO_TAP_OFF);
            System.out.println("Left ON Full Fare: $" + fare);
        }

        //  Charge full fare for remaining ON taps from starting bus stop
        System.out.println(toString());
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
