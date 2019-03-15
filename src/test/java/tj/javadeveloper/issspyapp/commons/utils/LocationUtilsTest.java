package tj.javadeveloper.issspyapp.commons.utils;

import org.junit.jupiter.api.Test;
import tj.javadeveloper.issspyapp.commons.exceptions.InvalidLocationDataException;
import tj.javadeveloper.issspyapp.domain.dto.LocationDto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void convertDataToLocationDtoMethodTest() {
        //given
        LocationDto expected = LocationDto.builder()
                .latitude(50.06465009)
                .longitude(19.94497990)
                .build();
        String latitude = "50.06465009";
        String longitude = "19.94497990";
        String latDir = "NORTH";
        String lonDir = "EAST";

        //when
        LocationDto actual = LocationUtils.convertDataToLocationDto(latitude, latDir, longitude, lonDir);

        //then
        assertNotNull(actual);
        assertAll(
                () -> assertEquals(expected.getLatitude(), actual.getLatitude()),
                () -> assertEquals(expected.getLongitude(), actual.getLongitude())
        );
    }

    @Test
    void convertDataToLocationDtoMethodTestShouldThrowExceptionIfInsertedDataIsWrong() {
        //then
        assertThrows(InvalidLocationDataException.class, () -> LocationUtils
                .convertDataToLocationDto("50.06465009", "NORTH", "19.944dde97990", "EAST"));
        assertThrows(InvalidLocationDataException.class, () -> LocationUtils
                .convertDataToLocationDto("", "", "", ""));
        assertThrows(InvalidLocationDataException.class, () -> LocationUtils
                .convertDataToLocationDto("50.06465009", "CENTRAL", "19.94497990", "EAST"));
        assertThrows(InvalidLocationDataException.class, () -> LocationUtils
                .convertDataToLocationDto("defdgd", "NORTH", "19.94497990", "EAST"));
        assertThrows(InvalidLocationDataException.class, () -> LocationUtils
                .convertDataToLocationDto("120.255600", "NORTH", "19.94497990", "EAST"));
    }

    @Test
    void distanceInKmMethodTest() {
        //given
        LocationDto location1 = LocationDto.builder()
                .latitude(40.366633)
                .longitude(74.640832)
                .build();
        LocationDto location2 = LocationDto.builder()
                .latitude(42.443087)
                .longitude(76.488707)
                .build();
        Double expected = 277.5;

        //when
        Double actual = LocationUtils.distanceInKm(location1, location2);

        //then
        assertNotNull(actual);
        assertEquals(expected, actual, 0.1);

    }

    @Test
    void calculateTotalDistanceInKmMethodTest() {
        //given
        LocationDto location1 = LocationDto.builder()
                .latitude(40.366633)
                .longitude(74.640832)
                .build();
        LocationDto location2 = LocationDto.builder()
                .latitude(42.443087)
                .longitude(76.488707)
                .build();
        LocationDto location3 = LocationDto.builder()
                .latitude(40.366633)
                .longitude(74.640832)
                .build();
        Double expected = 277.5 * 2;
        List<LocationDto> locationList = Arrays.asList(location1, location2, location3);
        List<LocationDto> locationList2 = Arrays.asList(location1);
        List<LocationDto> locationList3 = Arrays.asList(location1, location3);


        //when
        Double actual = LocationUtils.calculateTotalDistanceInKm(locationList);
        Double actual2 = LocationUtils.calculateTotalDistanceInKm(locationList2);
        Double actual3 = LocationUtils.calculateTotalDistanceInKm(locationList3);
        //then
        assertNotNull(actual);
        assertEquals(expected, actual, 1);
        assertNotNull(actual2);
        assertEquals(0, actual2, 0.1);
        assertNotNull(actual3);
        assertEquals(0, actual3, 0.1);
    }

    @Test
    void calculateCurrentSpeedInKmPerHourMethodTest() {
        //given
        LocalDateTime time = LocalDateTime.of(2019, 02, 15, 18, 15, 00);
        LocalDateTime timePlusOneHour = time.plusHours(1);

        LocationDto location1 = LocationDto.builder()
                .latitude(40.366633)
                .longitude(74.640832)
                .time(Timestamp.valueOf(time).toInstant().getEpochSecond())
                .build();
        LocationDto location2 = LocationDto.builder()
                .latitude(42.443087)
                .longitude(76.488707)
                .time(Timestamp.valueOf(timePlusOneHour).toInstant().getEpochSecond())
                .build();

        Double expected = 277.5;

        //when
        Double actual = LocationUtils.calculateCurrentSpeedInKmPerHour(location1, location2);

        //then
        assertNotNull(actual);
        assertEquals(expected, actual, 0.1);
    }
}