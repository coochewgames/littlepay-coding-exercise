import java.time.Instant;

public class Tap {
    public enum TapStatus {
        ON,
        OFF
    }

    private final int id;
    private final Instant utc;
    private final TapStatus tapStatus;
    private final String stopId;
    private final String companyId;
    private final String busId;
    private final String PAN;

    public Tap(
            int id,
            Instant utc,
            String tapStatusString,
            String stopId,
            String companyId,
            String busId,
            String PAN) throws IllegalArgumentException {
        this.id = id;
        this.utc = utc;
        this.stopId = stopId;
        this.companyId = companyId;
        this.busId = busId;
        this.PAN = PAN;

        try {
            this.tapStatus = TapStatus.valueOf(tapStatusString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid tapStatus value. Allowed values are 'ON' and 'OFF'.");
        }
    }

    public int getId() {
        return id;
    }

    public Instant getUTC() {
        return utc;
    }

    public TapStatus getTapStatus() {
        return tapStatus;
    }

    public String getStopId() {
        return stopId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getBusId() {
        return busId;
    }

    public String getPAN() {
        return PAN;
    }

    @Override
    public String toString() {
        return "Tap [ID=" + id +
                ", DateTimeUTC=" + utc +
                ", TapType=" + tapStatus +
                ", StopId=" + stopId +
                ", CompanyId=" + companyId +
                ", BusID=" + busId +
                ", PAN=" + PAN + "]";
    }
}
