package translator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataUtil {

    /* Ajuste o formato para MM/dd/yyyy */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static LocalDate parseData(String data) {
        data = data.replace("\"", "").trim();
        return LocalDate.parse(data, FORMATTER);
    }
}
