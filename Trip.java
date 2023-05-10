import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

public class Trip {
    public enum Status {
        CANCELLED("CANCELLED"),
        COMPLETED("COMPLETED"),
        INCOMPLETED("INCOMPLETED");
    
        private final String statusName;
    
        Status(String statusName) {
            this.statusName = statusName;
        }
    
        public String getstatusName() {
            return statusName;
        }
    }

    private Instant started;
    private Instant finished;
    private long durationSecs;
    private BusStop fromStopId;
    private BusStop toStopId;
    private BigDecimal chargeAmount;
    private String companyId;
    private String busId;
    private String PAN;
    private Status status;

    public Trip(Instant started,
            Instant finished,
            long durationSecs,
            BusStop fromStopId,
            BusStop toStopId,
            BigDecimal chargeAmount,
            String companyId,
            String busId,
            String PAN,
            Status status) {
        this.started = started;
        this.finished = finished;
        this.durationSecs = durationSecs;
        this.fromStopId = fromStopId;
        this.toStopId = toStopId;
        this.chargeAmount = chargeAmount;
        this.companyId = companyId;
        this.busId = busId;
        this.PAN = PAN;
        this.status = status;
    }

    public String header() {
        return "Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN, Status";
    }

    public String csv() {
        return DateHandler.InstantToString(started) + ", " +
                DateHandler.InstantToString(finished) + ", " +
                durationSecs + ", " +
                fromStopId + ", " +
                toStopId + ", " +
                "$" + chargeAmount.setScale(2, RoundingMode.HALF_UP).toString() + ", " +
                companyId + ", " +
                busId + ", " +
                PAN + ", " +
                status;
    }

    @Override
    public String toString() {
        return "TripRecord{" +
                "started=" + DateHandler.InstantToString(started) +
                ", finished=" + DateHandler.InstantToString(finished) +
                ", durationSecs=" + durationSecs +
                ", fromStopId='" + fromStopId + '\'' +
                ", toStopId='" + toStopId + '\'' +
                ", chargeAmount=" + chargeAmount +
                ", companyId='" + companyId + '\'' +
                ", busId='" + busId + '\'' +
                ", pan='" + PAN + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
