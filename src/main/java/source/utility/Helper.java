package source.utility;

import java.time.format.DateTimeFormatter;

public class Helper {
    public static DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public static DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void ValidateStringIsNullOrEmpty(String name, String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException(name + " is null or empty.");
        }
    }
}
