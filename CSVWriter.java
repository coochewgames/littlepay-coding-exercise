import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.math.RoundingMode;

public class CSVWriter {
    private String filename;

    public CSVWriter(String filename) {
        this.filename = filename;
    }

    public boolean writeCSVFile(List<Trip> tripRecordList) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(tripHeader());
            writer.newLine();

            // Write data
            for (Trip trip : tripRecordList) {
                writer.write(tripToCSV(trip));
                writer.newLine();
            }
        } catch (Exception e) {
            throw (e);
        }

        return false;
    }

    public static String tripHeader() {
        return "Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN, Status";
    }

    public String tripToCSV(Trip trip) {
        return DateHandler.InstantToString(trip.getStarted()) + ", " +
                DateHandler.InstantToString(trip.getFinished()) + ", " +
                trip.getDurationSecs() + ", " +
                trip.getFromStopId().getStopName() + ", " +
                trip.getToStopId().getStopName() + ", " +
                "$" + trip.getChargeAmount().setScale(2, RoundingMode.HALF_UP).toString() + ", " +
                trip.getCompanyId() + ", " +
                trip.getBusId() + ", " +
                trip.getPAN() + ", " +
                trip.getStatus();
    }
}
