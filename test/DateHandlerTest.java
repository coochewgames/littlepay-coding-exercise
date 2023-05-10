import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;

public class DateHandlerTest {
    @Test
    public void testStringToInstant() {
        String input = "22-01-2018 13:00:00";
        Instant expected = LocalDateTime.of(2018, 1, 22, 13, 0, 0).toInstant(ZoneOffset.UTC);

        assertEquals(expected, DateHandler.StringToInstant(input));
    }

    @Test
    public void testInstantToString() {
        Instant input = LocalDateTime.of(2018, 1, 22, 13, 0, 0).toInstant(ZoneOffset.UTC);
        String expected = "22-01-2018 13:00:00";

        assertEquals(expected, DateHandler.InstantToString(input));
    }

    @Test
    public void testInstantToString_nullInput() {
        assertEquals("N/A", DateHandler.InstantToString(null));
    }

    @Test
    public void testStringToInstant_invalidFormat() {
        String input = "22-01-2018T13:00:00";
        assertThrows(DateTimeParseException.class, () -> DateHandler.StringToInstant(input));
    }

    @Test
    public void testStringToInstant_invalidDate() {
        String input = "32-01-2018 13:00:00";
        assertThrows(DateTimeParseException.class, () -> DateHandler.StringToInstant(input));
    }
}
