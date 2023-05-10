import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZoneId;

public class DateHandler {
    public static Instant StringToInstant(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime utcLocal = LocalDateTime.parse(dateString, formatter);

        return utcLocal.toInstant(ZoneOffset.UTC);
    }

    public static String InstantToString(Instant date) {
        if (date == null ) {
            return "N/A";
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return formatter.format(date.atZone(ZoneId.of("UTC")));
    }
}
