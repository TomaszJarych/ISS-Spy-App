package tj.javadeveloper.issspyapp.commons.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationUtilsTest {

    @Test
    void toLocalDateTimeFromTimestamp() {
        //given
        Long timestamp = 1552393828L;
        LocalDateTime expected = LocalDateTime.of(2019, 3, 12, 13, 30, 28);

        //when
        LocalDateTime actual = LocationUtils.toLocalDateTimeFromTimestamp(timestamp);

        // then
        assertAll(
                () -> assertEquals(expected.getYear(), actual.getYear()),
                () -> assertEquals(expected.getMonth(), actual.getMonth()),
                () -> assertEquals(expected.getDayOfMonth(), actual.getDayOfMonth()),
                () -> assertEquals(expected.getHour(), actual.getHour()),
                () -> assertEquals(expected.getMinute(), actual.getMinute()),
                () -> assertEquals(expected.getSecond(), actual.getSecond()
                ));

    }
}