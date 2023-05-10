import java.io.IOException;
import java.util.List;


public class LittlePay {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java LittlePay <filename> [separator]");
            System.exit(1);
        }

        String filename = args[0];
        String separator = CSVReader.DEFAULT_SEPARATOR;

        if (args.length > 1) {
            separator = args[1];
        }

        try {
            CSVReader csvReader = new CSVReader(filename, separator);
            List<Tap> tapList = csvReader.readCSVFile();
            BusStopProcessor bus = new BusStopProcessor();

            bus.processTaps(tapList);
        } catch (IOException ioe) {
            System.err.println("Error reading CSV file (" + filename + "): " + ioe.getMessage());
            System.exit(10);
        } catch (java.time.format.DateTimeParseException dtpe) {
            System.err.println("Error parsing the date in CSV file (" + filename + "): " + dtpe.getMessage());
            System.exit(11);
        } catch (Exception e) {
            System.err.println("Error occurred parsing the CSV file (" + filename + "): " + e.getMessage());
            System.exit(12);
        }
    }
}
