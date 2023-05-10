import java.io.IOException;
import java.util.List;
import java.time.Instant;


public class LittlePay {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java LittlePay <filename> [separator] [process UTC: dd/MM/yyyy HH:mm:ss]");
            System.exit(1);
        }

        String filename = args[0];
        String separator = CSVReader.DEFAULT_SEPARATOR;
        Instant utcProcessing = null;

        if (args.length > 1) {
            separator = args[1];
        }

        if (args.length > 2) {
            try {
                utcProcessing = DateHandler.StringToUTC(args[2]);
            } catch (Exception e) {
                System.err.println("Error occurred parsing the processing UTC (" + args[2] + "): " + e.getMessage());
                System.exit(2);
            }
        }

        try {
            CSVReader csvReader = new CSVReader(filename, separator);
            List<Tap> tapList = csvReader.readCSVFile();
            BusStopProcessor bus = new BusStopProcessor(utcProcessing);

            bus.processTaps(tapList);
        } catch (IOException ioe) {
            System.err.println("Error reading CSV file (" + filename + "): " + ioe.getMessage());
            System.exit(3);
        } catch (java.time.format.DateTimeParseException dtpe) {
            System.err.println("Error parsing the date in CSV file (" + filename + "): " + dtpe.getMessage());
            System.exit(4);
        } catch (Exception e) {
            System.err.println("Error occurred parsing the CSV file (" + filename + "): " + e.getMessage());
            System.exit(5);
        }
    }
}
