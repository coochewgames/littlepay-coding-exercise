import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;


public class CSVWriter {
    private String filename;

    public CSVWriter(String filename) {
        this.filename = filename;
    }

    public boolean writeCSVFile(List<Trip> tripRecordList) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(Trip.header());
            writer.newLine();

            // Write data
            for (Trip trip : tripRecordList) {
                writer.write(trip.csv());
                writer.newLine();
            }
        } catch (Exception e) {
            throw(e);
        }

        return false;
    }
}
