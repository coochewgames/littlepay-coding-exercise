import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
    public static void main(String[] args) {
        String outputFile = "output.csv";

        // Header
        String header = "Started,Finished,DurationSecs,FromStopId,ToStopId,ChargeAmount,CompanyId,BusID,PAN,Status";

        // Data
        String data = "22-01-2018 13:00:00,22-01-2018 13:05:00,900,Stop1,Stop2,$3.25,Company1,B37,5500005555555559,COMPLETED";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            // Write header
            writer.write(header);
            writer.newLine();

            // Write data
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
