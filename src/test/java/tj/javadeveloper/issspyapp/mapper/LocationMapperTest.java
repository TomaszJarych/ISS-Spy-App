package tj.javadeveloper.issspyapp.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tj.javadeveloper.issspyapp.domain.dto.LocationDto;
import tj.javadeveloper.issspyapp.domain.entity.LocationEntity;
import tj.javadeveloper.issspyapp.domain.resttempalte.GeoCoordinates;
import tj.javadeveloper.issspyapp.domain.resttempalte.ISSLocation;

import static org.junit.jupiter.api.Assertions.*;

class LocationMapperTest {

    private LocationMapper mapper;
    private String message;
    private Long timestamp;
    private String sourceLatitude;
    private String sourceLongitude;
    private Double parsedLatitude;
    private Double parsedLongitude;

    @BeforeEach
    void setUp() {
        mapper = LocationMapper.INSTANCE;
        message = "success";
        timestamp = 1552393828L;
        sourceLatitude = "-49.0075";
        sourceLongitude = "58.6387";
        parsedLatitude = Double.parseDouble(sourceLatitude);
        parsedLongitude = Double.parseDouble(sourceLongitude);
    }

    @Test
    void issLocationToLocationDtoMapper() {
        //given
        GeoCoordinates geoCoordinates = GeoCoordinates.builder()
                .latitude(sourceLatitude)
                .longitude(sourceLongitude)
                .build();
        ISSLocation issLocation = ISSLocation.builder()
                .message(message)
                .timestamp(timestamp)
                .issPosition(geoCoordinates)
                .build();
        LocationDto expected = LocationDto.builder()
                .time(timestamp)
                .latitude(parsedLatitude)
                .longitude(parsedLongitude)
                .build();

        // when
        LocationDto actual = mapper.toLocationDto(issLocation);

        //then
        assertAll(
                () -> assertEquals(expected.getTime(), actual.getTime()),
                () -> assertEquals(expected.getLatitude(), actual.getLatitude()),
                () -> assertEquals(expected.getLongitude(), actual.getLongitude())
        );
    }

    @Test
    void toLocationEntityConverterMethodTest() {
        //given
        LocationDto dto = LocationDto.builder()
                .id(null)
                .time(timestamp)
                .latitude(parsedLatitude)
                .longitude(parsedLongitude)
                .build();
        LocationEntity expected = LocationEntity.builder()
                .id(null)
                .time(timestamp)
                .latitude(parsedLatitude)
                .longitude(parsedLongitude)
                .build();

        //when
        LocationEntity actual = mapper.toLocationEntity(dto);


        //then
        assertNotNull(actual);
        assertAll(
                () -> assertEquals(expected.getTime(), actual.getTime()),
                () -> assertEquals(expected.getLatitude(), actual.getLatitude()),
                () -> assertEquals(expected.getLongitude(), actual.getLongitude())
        );
    }

    @Test
    void toLocationDtoFromLocationEntityConverterMethodTest() {
        //given
        LocationEntity entity = LocationEntity.builder()
                .id(1L)
                .time(timestamp)
                .latitude(parsedLatitude)
                .longitude(parsedLongitude)
                .build();

        LocationDto expected = LocationDto.builder()
                .id(1L)
                .time(timestamp)
                .latitude(parsedLatitude)
                .longitude(parsedLongitude)
                .build();
        //when
        LocationDto actual = mapper.fromLocationEntityToLocationDto(entity);
        assertNotNull(actual);
        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getTime(), actual.getTime()),
                () -> assertEquals(expected.getLatitude(), actual.getLatitude()),
                () -> assertEquals(expected.getLongitude(), actual.getLongitude())
        );
    }
}