import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;

public class TapTest {
    @Test
    public void testConstructorAndGetters() {
        Instant testInstant = Instant.parse("2018-01-22T13:00:00Z");
        Tap tap = new Tap(1, testInstant, "ON", BusStop.STOP_1, "Company1", "Bus37", "5500005555555559");

        assertEquals(1, tap.getId());
        assertEquals(testInstant, tap.getUTC());
        assertEquals(Tap.TapStatus.ON, tap.getTapStatus());
        assertEquals(BusStop.STOP_1, tap.getStopId());
        assertEquals("Company1", tap.getCompanyId());
        assertEquals("Bus37", tap.getBusId());
        assertEquals("5500005555555559", tap.getPAN());
    }

    @Test
    public void testInvalidTapStatus() {
        Instant testInstant = Instant.parse("2018-01-22T13:00:00Z");

        assertThrows(IllegalArgumentException.class, () -> {
            new Tap(1, testInstant, "INVALID", BusStop.STOP_1, "Company1", "Bus37", "5500005555555559");
        });
    }

    @Test
    public void testToString() {
        Instant testInstant = Instant.parse("2018-01-22T13:00:00Z");
        Tap tap = new Tap(1, testInstant, "ON", BusStop.STOP_1, "Company1", "Bus37", "5500005555555559");

        String expected = "Tap [ID=1, DateTimeUTC=2018-01-22T13:00:00Z, TapType=ON, StopId=Stop1, CompanyId=Company1, BusID=Bus37, PAN=5500005555555559]";
        assertEquals(expected, tap.toString());
    }
}
