import java.io.IOException;
import java.util.List;


public class LittlePay {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java LittlePay <taps CSV input filename> [separator] [trips CSV output filename]");
            System.exit(1);
        }

        String tapsFilename = args[0];
        String tripFilename = "trips.csv";
        String separator = CSVReader.DEFAULT_SEPARATOR;
        int exitStatus = 0;

        if (args.length > 1) {
            separator = args[1];
        }

        if (args.length > 2) {
            tripFilename = args[2];
        }

        try {
            CSVReader csvReader = new CSVReader(tapsFilename, separator);
            List<Tap> tapList = csvReader.readCSVFile();
            FareProcessor fareProcessor = new FareProcessor();
            BusStopProcessor bsp = new BusStopProcessor(fareProcessor);

            tapsFilename = tripFilename;
            List<Trip> tripList = bsp.processTaps(tapList);

            try {
                CSVWriter csvWriter = new CSVWriter(tripFilename);
    
                csvWriter.writeCSVFile(tripList);
            } catch (Exception e) {
                System.err.println("Error occurred writing the CSV file (" + tripFilename + "): " + e.getMessage());
                exitStatus = 20;
            }
        } catch (IOException ioe) {
            System.err.println("Error reading CSV file (" + tapsFilename + "): " + ioe.getMessage());
            exitStatus = 10;
        } catch (java.time.format.DateTimeParseException dtpe) {
            System.err.println("Error parsing the date in CSV file (" + tapsFilename + "): " + dtpe.getMessage());
            exitStatus = 11;
        } catch (Exception e) {
            System.err.println("Error occurred parsing the CSV file (" + tapsFilename + "): " + e.getMessage());
            exitStatus = 12;
        }

        System.exit(exitStatus);
    }
}
