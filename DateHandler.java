import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.Instant;
import java.time.ZoneOffset;

public class DateHandler {
    public static Instant StringToUTC(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime utcLocal = LocalDateTime.parse(dateString, formatter);

        return utcLocal.toInstant(ZoneOffset.UTC);
    }
}
