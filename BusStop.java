public enum BusStop {
    STOP_1("Stop1"),
    STOP_2("Stop2"),
    STOP_3("Stop3"),
    NO_TAP_OFF("None");

    private final String stopName;

    BusStop(String stopName) {
        this.stopName = stopName;
    }

    public String getStopName() {
        return stopName;
    }

    public static BusStop fromValue(String value) {
        for (BusStop stop : BusStop.values()) {
            if (stop.getStopName().equals(value)) {
                return stop;
            }
        }

        throw new IllegalArgumentException("Unable to locate bus stop: " + value);
    }
}
