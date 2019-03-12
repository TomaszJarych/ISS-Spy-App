package tj.javadeveloper.issspyapp.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tj.javadeveloper.issspyapp.domain.dto.LocationDto;
import tj.javadeveloper.issspyapp.domain.resttempalte.GeoCoordinates;
import tj.javadeveloper.issspyapp.domain.resttempalte.ISSLocation;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}