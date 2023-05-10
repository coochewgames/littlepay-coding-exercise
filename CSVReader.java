import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private String csvSeparator;
    private String filename;

    public static final String DEFAULT_SEPARATOR = ",";

    public CSVReader(String filename) {
        this(filename, DEFAULT_SEPARATOR);
    }

    public CSVReader(String filename, String csvSeparator) {
        this.csvSeparator = csvSeparator;
        this.filename = filename;
    }

    public List<Tap> readCSVFile() throws IOException, java.time.format.DateTimeParseException {
        List<Tap> tapRecordList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine();  // Ignore header

            String line;

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(csvSeparator);

                int id = Integer.parseInt(fields[0].trim());
                Instant utc = DateHandler.StringToUTC(fields[1].trim());
                String tapStatus = fields[2].trim();
                String stopId = fields[3].trim();
                String companyId = fields[4].trim();
                String busId = fields[5].trim();
                String PAN = fields[6].trim();

                Tap tapRecord = new Tap(id, utc, tapStatus, stopId, companyId, busId, PAN);
                tapRecordList.add(tapRecord);
            }
        } catch (Exception e) {
            throw e;
        }

        return tapRecordList;
    }
}
