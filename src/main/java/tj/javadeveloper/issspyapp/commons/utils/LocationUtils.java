package tj.javadeveloper.issspyapp.commons.utils;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocationUtils {

    public static LocalDateTime toLocalDateTimeFromTimestamp(Long timestamp) {
        LocalDateTime dateTime =
                LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
        System.out.println(dateTime);
        return dateTime;
    }
}
