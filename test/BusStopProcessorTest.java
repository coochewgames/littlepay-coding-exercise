import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Instant;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class BusStopProcessorTest {
    @Test
    public void testProcessTaps() {
        Tap tap1 = new Tap(1, Instant.parse("2020-01-01T10:00:00Z"), "ON", BusStop.STOP_1, "Company1", "Bus1", "PAN1");
        Tap tap2 = new Tap(2, Instant.parse("2020-01-01T10:05:00Z"), "OFF", BusStop.STOP_2, "Company1", "Bus1", "PAN1");
        Tap tap3 = new Tap(3, Instant.parse("2020-01-01T10:10:00Z"), "ON", BusStop.STOP_2, "Company1", "Bus1", "PAN2");
        Tap tap4 = new Tap(4, Instant.parse("2020-01-01T10:15:00Z"), "OFF", BusStop.STOP_3, "Company1", "Bus1", "PAN2");
        Tap tap5 = new Tap(5, Instant.parse("2020-01-01T10:20:00Z"), "ON", BusStop.STOP_1, "Company1", "Bus1", "PAN3");
        Tap tap6 = new Tap(6, Instant.parse("2020-01-01T10:25:00Z"), "OFF", BusStop.STOP_2, "Company1", "Bus2", "PAN3");

        List<Tap> tapList = Arrays.asList(tap1, tap2, tap3, tap4, tap5, tap6);

        BusStopProcessor processor = new BusStopProcessor();
        List<Trip> tripList = processor.processTaps(tapList);

        assertEquals(3, tripList.size(), "Expected 3 trips to be processed");

        // Positive test cases
        Trip trip1 = tripList.get(0);
        assertEquals(Trip.Status.COMPLETED, trip1.getStatus(), "Expected trip1 to have a status of COMPLETED");
        assertEquals(new BigDecimal("3.25"), trip1.getChargeAmount(), "Expected trip1 to have a fare of $3.25");

        Trip trip2 = tripList.get(1);
        assertEquals(Trip.Status.COMPLETED, trip2.getStatus(), "Expected trip2 to have a status of COMPLETED");
        assertEquals(new BigDecimal("5.50"), trip2.getChargeAmount(), "Expected trip2 to have a fare of $5.50");

        // Negative test cases
        Trip trip3 = tripList.get(2);
        assertEquals(Trip.Status.COMPLETED, trip3.getStatus(), "Expected trip3 to have a status of COMPLETED");
        assertEquals(new BigDecimal("3.25"), trip3.getChargeAmount(), "Expected trip3 to have a fare of $7.30");
    }
}
