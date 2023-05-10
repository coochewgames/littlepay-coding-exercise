import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVWriterTest {

    @Test
    public void testWriteCSVFile(@TempDir Path tempDir) throws Exception {
        Path outputFile = tempDir.resolve("output.csv");
        CSVWriter csvWriter = new CSVWriter(outputFile.toString());

        List<Trip> tripRecordList = new ArrayList<>();

        Instant started = Instant.parse("2018-01-22T13:00:00Z");
        Instant finished = Instant.parse("2018-01-22T13:05:00Z");
        long durationSecs = 300;
        BigDecimal chargeAmount = new BigDecimal("3.25");
        Trip trip = new Trip(started, finished, durationSecs, BusStop.STOP_1, BusStop.STOP_2, chargeAmount, "Company1", "Bus37", "5500005555555559", Trip.Status.COMPLETED);
        tripRecordList.add(trip);

        csvWriter.writeCSVFile(tripRecordList);

        List<String> lines = Files.readAllLines(outputFile);
        String expectedHeader = "Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN, Status";
        String expectedTripLine = "22-01-2018 13:00:00, 22-01-2018 13:05:00, 300, Stop1, Stop2, $3.25, Company1, Bus37, 5500005555555559, COMPLETED";

        assertEquals(2, lines.size());
        assertEquals(expectedHeader, lines.get(0));
        assertEquals(expectedTripLine, lines.get(1));
    }
}
