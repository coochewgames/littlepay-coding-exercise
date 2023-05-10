import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BusStopProcessorTest {

    private BusStopProcessor busStopProcessor;

    @BeforeEach
    public void setUp() {
        busStopProcessor = new BusStopProcessor();
    }

    @Test
    public void testProcessTapsPositive() {
        List<Tap> tapList = Arrays.asList(
                new Tap(1, Instant.parse("2020-01-01T10:00:00Z"), "ON", BusStop.STOP_1, "Company1", "Bus1", "PAN1"),
                new Tap(2, Instant.parse("2020-01-01T10:05:00Z"), "OFF", BusStop.STOP_2, "Company1", "Bus1", "PAN1"),
                new Tap(3, Instant.parse("2020-01-01T10:10:00Z"), "ON", BusStop.STOP_2, "Company1", "Bus1", "PAN2"),
                new Tap(4, Instant.parse("2020-01-01T10:15:00Z"), "OFF", BusStop.STOP_3, "Company1", "Bus1", "PAN2")
        );

        List<Trip> tripList = busStopProcessor.processTaps(tapList);

        assertEquals(2, tripList.size());

        Trip trip1 = tripList.get(0);
        assertEquals(Trip.Status.COMPLETED, trip1.getStatus());
        assertEquals(new BigDecimal("3.25"), trip1.getChargeAmount());
        assertEquals(Duration.ofMinutes(5).toSeconds(), trip1.getDurationSecs());

        Trip trip2 = tripList.get(1);
        assertEquals(Trip.Status.COMPLETED, trip2.getStatus());
        assertEquals(new BigDecimal("5.50"), trip2.getChargeAmount());
        assertEquals(Duration.ofMinutes(5).toSeconds(), trip2.getDurationSecs());
    }

    @Test
    public void testProcessTapsNegative() {
        List<Tap> tapList = Arrays.asList(
                new Tap(1, Instant.parse("2020-01-01T10:00:00Z"), "ON", BusStop.STOP_1, "Company1", "Bus1", "PAN1"),
                new Tap(2, Instant.parse("2020-01-01T10:05:00Z"), "OFF", BusStop.STOP_2, "Company1", "Bus2", "PAN1"),
                new Tap(3, Instant.parse("2020-01-01T10:10:00Z"), "ON", BusStop.STOP_2, "Company1", "Bus1", "PAN2"),
                new Tap(4, Instant.parse("2020-01-01T10:15:00Z"), "OFF", BusStop.STOP_3, "Company2", "Bus1", "PAN2")
        );
    
        List<Trip> tripList = busStopProcessor.processTaps(tapList);
    
        assertEquals(2, tripList.size());
    
        Trip trip1 = tripList.get(0);
        assertEquals(Trip.Status.COMPLETED, trip1.getStatus());
        assertEquals(new BigDecimal("3.25"), trip1.getChargeAmount());
        assertEquals(Duration.ofMinutes(5).toSeconds(), trip1.getDurationSecs());
    
        Trip trip2 = tripList.get(1);
        assertEquals(Trip.Status.COMPLETED, trip2.getStatus());
        assertEquals(new BigDecimal("5.50"), trip2.getChargeAmount());
        assertEquals(Duration.ofMinutes(5).toSeconds(), trip2.getDurationSecs());
    }

    @Test
    public void testProcessTaps() {
        Tap tap1 = new Tap(5, Instant.parse("2020-01-01T10:20:00Z"), "ON", BusStop.STOP_1, "Company1", "Bus1", "PAN3");
        Tap tap2 = new Tap(6, Instant.parse("2020-01-01T10:25:00Z"), "OFF", BusStop.STOP_1, "Company1", "Bus1", "PAN3");

        List<Tap> tapList = Arrays.asList(tap1, tap2);

        BusStopProcessor processor = new BusStopProcessor();
        List<Trip> tripList = processor.processTaps(tapList);

        assertEquals(1, tripList.size(), "Expected 3 trips to be processed");

        Trip trip1 = tripList.get(0);
        assertEquals(Trip.Status.CANCELLED, trip1.getStatus(), "Expected trip3 to have a status of CANCELLED");
        assertEquals(new BigDecimal("0.00"), trip1.getChargeAmount(), "Expected trip3 to have a fare of $0.00");
    }
}
    