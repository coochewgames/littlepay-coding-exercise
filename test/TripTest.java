import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TripTest {

    @Test
    public void testTrip() {
        Instant started = Instant.parse("2018-01-22T13:00:00Z");
        Instant finished = Instant.parse("2018-01-22T13:05:00Z");
        long durationSecs = 300;
        BusStop fromStopId = BusStop.STOP_1;
        BusStop toStopId = BusStop.STOP_2;
        BigDecimal chargeAmount = new BigDecimal("3.25");
        String companyId = "Company1";
        String busId = "Bus37";
        String PAN = "5500005555555559";
        Trip.Status status = Trip.Status.COMPLETED;

        Trip trip = new Trip(started, finished, durationSecs, fromStopId, toStopId, chargeAmount, companyId, busId, PAN, status);

        assertEquals(started, trip.getStarted());
        assertEquals(finished, trip.getFinished());
        assertEquals(durationSecs, trip.getDurationSecs());
        assertEquals(fromStopId, trip.getFromStopId());
        assertEquals(toStopId, trip.getToStopId());
        assertEquals(chargeAmount, trip.getChargeAmount());
        assertEquals(companyId, trip.getCompanyId());
        assertEquals(busId, trip.getBusId());
        assertEquals(PAN, trip.getPAN());
        assertEquals(status, trip.getStatus());
    }

    @Test
    public void testStatusEnum() {
        assertEquals("CANCELLED", Trip.Status.CANCELLED.getStatusName());
        assertEquals("COMPLETED", Trip.Status.COMPLETED.getStatusName());
        assertEquals("INCOMPLETED", Trip.Status.INCOMPLETED.getStatusName());
    }

    @Test
    public void testToString() {
        Instant started = Instant.parse("2018-01-22T13:00:00Z");
        Instant finished = Instant.parse("2018-01-22T13:05:00Z");
        long durationSecs = 300;
        BusStop fromStopId = BusStop.STOP_1;
        BusStop toStopId = BusStop.STOP_2;
        BigDecimal chargeAmount = new BigDecimal("3.25");
        String companyId = "Company1";
        String busId = "Bus37";
        String PAN = "5500005555555559";
        Trip.Status status = Trip.Status.COMPLETED;

        Trip trip = new Trip(started, finished, durationSecs, fromStopId, toStopId, chargeAmount, companyId, busId, PAN, status);

        String expectedToString = "TripRecord{" +
                "started=22-01-2018 13:00:00" +
                ", finished=22-01-2018 13:05:00" +
                ", durationSecs=300" +
                ", fromStopId='Stop1'" +
                ", toStopId='Stop2'" +
                ", chargeAmount=3.25" +
                ", companyId='Company1'" +
                ", busId='Bus37'" +
                ", pan='5500005555555559'" +
                ", status='COMPLETED'" +
                '}';
        assertEquals(expectedToString, trip.toString());
    }
}
