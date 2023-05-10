import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CSVReaderTest {

    @Test
    public void testReadCSVFile_ValidCSV() throws Exception {
        String validCSVContent = "ID,DateTimeUTC,TapType,StopId,CompanyId,BusID,PAN\n" +
                "1,22-01-2018 13:00:00,ON,Stop1,Company1,Bus37,5500005555555559\n" +
                "2,22-01-2018 13:05:00,OFF,Stop2,Company1,Bus37,5500005555555559";
        Path validCSVPath = createTempFileWithContent(validCSVContent);

        CSVReader csvReader = new CSVReader(validCSVPath.toString());
        List<Tap> taps = csvReader.readCSVFile();

        assertEquals(2, taps.size());
    }

    @Test
    public void testReadCSVFile_InvalidCSV() throws Exception {
        String invalidCSVContent = "ID,DateTimeUTC,TapType,StopId,CompanyId,BusID,PAN\n" +
                "1,22-01-2018 13:00:00,INVALID,Stop1,Company1,Bus37,5500005555555559";
        Path invalidCSVPath = createTempFileWithContent(invalidCSVContent);

        CSVReader csvReader = new CSVReader(invalidCSVPath.toString());

        assertThrows(IllegalArgumentException.class, () -> csvReader.readCSVFile());
    }

    private Path createTempFileWithContent(String content) throws IOException {
        Path tempFile = Files.createTempFile("test", ".csv");
        Files.writeString(tempFile, content);
        return tempFile;
    }
}
