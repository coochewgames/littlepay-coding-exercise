import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FareProcessorTest {
    private FareProcessor fareProcessor;

    @BeforeEach
    public void setUp() {
        fareProcessor = new FareProcessor();
    }

    @Test
    public void testGetFare_ValidStops() {
        assertEquals(new BigDecimal("3.25"), fareProcessor.getFare(BusStop.STOP_1, BusStop.STOP_2));
        assertEquals(new BigDecimal("7.30"), fareProcessor.getFare(BusStop.STOP_1, BusStop.STOP_3));
        assertEquals(new BigDecimal("5.50"), fareProcessor.getFare(BusStop.STOP_2, BusStop.STOP_3));
    }

    @Test
    public void testGetFare_InvalidStartStop() {
        assertThrows(NullPointerException.class, () -> fareProcessor.getFare(null, BusStop.STOP_2));
    }

    @Test
    public void testGetFare_SameStartAndEndStop() {
        assertEquals(new BigDecimal("0.00"), fareProcessor.getFare(BusStop.STOP_1, BusStop.STOP_1));
        assertEquals(new BigDecimal("0.00"), fareProcessor.getFare(BusStop.STOP_2, BusStop.STOP_2));
        assertEquals(new BigDecimal("0.00"), fareProcessor.getFare(BusStop.STOP_3, BusStop.STOP_3));
    }

    @Test
    public void testGetFare_NoTapOff() {
        assertEquals(new BigDecimal("7.30"), fareProcessor.getFare(BusStop.STOP_1, BusStop.NO_TAP_OFF));
        assertEquals(new BigDecimal("5.50"), fareProcessor.getFare(BusStop.STOP_2, BusStop.NO_TAP_OFF));
        assertEquals(new BigDecimal("7.30"), fareProcessor.getFare(BusStop.STOP_3, BusStop.NO_TAP_OFF));
    }
}
