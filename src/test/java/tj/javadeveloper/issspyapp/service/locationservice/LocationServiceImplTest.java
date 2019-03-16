package tj.javadeveloper.issspyapp.service.locationservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import tj.javadeveloper.issspyapp.commons.exceptions.ExternalServiceConnectionFailedException;
import tj.javadeveloper.issspyapp.commons.utils.LocationUtils;
import tj.javadeveloper.issspyapp.domain.dto.LocationDto;
import tj.javadeveloper.issspyapp.domain.dto.UserLocationResult;
import tj.javadeveloper.issspyapp.domain.resttempalte.GeoCoordinates;
import tj.javadeveloper.issspyapp.domain.resttempalte.IPLocationData;
import tj.javadeveloper.issspyapp.domain.resttempalte.ISSLocation;
import tj.javadeveloper.issspyapp.mapper.LocationMapper;
import tj.javadeveloper.issspyapp.mapper.PredictedPassMapper;
import tj.javadeveloper.issspyapp.repository.LocationRepository;
import tj.javadeveloper.issspyapp.service.resttemplateservice.RestTemplateFetchService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {
    @Mock
    private RestTemplateFetchService fetchService;
    @Mock
    private LocationRepository locationRepository;

    private PredictedPassMapper predictedPassMapper;
    private LocationMapper locationMapper;

    private LocationServiceImpl service;

    @BeforeEach
    void setUp() {
        predictedPassMapper = PredictedPassMapper.INSTANCE;
        locationMapper = LocationMapper.INSTANCE;
        service = new LocationServiceImpl(fetchService, locationMapper, locationRepository, predictedPassMapper);
    }

    @Test
    void getCurrentLocationMethodTest() {
        //given
        GeoCoordinates coordinates = GeoCoordinates.builder()
                .latitude("50.06465009")
                .longitude("19.9449799")
                .build();
        ISSLocation stub = ISSLocation.builder()
                .message("sucess")
                .timestamp(1552746817L)
                .issPosition(coordinates)
                .build();
        LocationDto expected = LocationDto.builder()
                .latitude(50.06465009)
                .longitude(19.9449799)
                .time(1552746817L)
                .build();
        when(fetchService.getCurrentLocation()).thenReturn(stub);
        //when
        LocationDto actual = service.getCurrentLocation();
        //then
        assertNotNull(actual);
        assertAll("GetCurrentPostion() test",
                () -> assertEquals(expected.getTime(), actual.getTime()),
                () -> assertEquals(expected.getLongitude(), actual.getLongitude()),
                () -> assertEquals(expected.getLatitude(), actual.getLatitude())
        );
        verify(fetchService, atLeastOnce()).getCurrentLocation();
    }

    @Test
    void getCurrentLocationMethodTestShouldThrowExceptionWhenFetchServiceIsUnreachable() {
        //given
        when(fetchService.getCurrentLocation()).thenThrow(HttpClientErrorException.class);
        //then
        assertThrows(ExternalServiceConnectionFailedException.class, (
                () -> service.getCurrentLocation()));
    }

    @Test
    void getCurrentLocationMethodTestShouldThrowExceptionWhenFetchServiceThrowsResourcessAccessException() {
        //when
        when(fetchService.getCurrentLocation()).thenThrow(ResourceAccessException.class);
        //then
        assertThrows(ExternalServiceConnectionFailedException.class, (
                () -> service.getCurrentLocation()));
    }


    @Test
    void getCurrentSpeed() {
        //given
        GeoCoordinates firstCoordinates = GeoCoordinates.builder()
                .latitude("-59.9689")
                .longitude("-47.7676")
                .build();
        GeoCoordinates secondCoordinates = GeoCoordinates.builder()
                .latitude("-59.6718")
                .longitude("-47.8540")
                .build();
        ISSLocation firstLocation = ISSLocation.builder()
                .issPosition(firstCoordinates)
                .message("success")
                .timestamp(1552749160L)
                .build();

        ISSLocation secondLocation = ISSLocation.builder()
                .issPosition(secondCoordinates)
                .message("success")
                .timestamp(1552749164L)
                .build();
        when(fetchService.getCurrentLocation()).thenReturn(firstLocation, secondLocation);


        double expected = LocationUtils.calculateCurrentSpeedInKmPerHour(locationMapper.toLocationDto(firstLocation),
                locationMapper.toLocationDto(secondLocation));

        //when
        double actual = service.getCurrentSpeed();

        assertNotNull(actual);
        assertAll(
                () -> assertEquals(expected, actual, 0.1)
        );
        verify(fetchService, Mockito.times(2)).getCurrentLocation();
    }

    @Test
    void getCurrentSpeedShouldThrowIExternalServiceConnectionFailedException() {
        when(fetchService.getCurrentLocation()).thenThrow(ResourceAccessException.class, HttpClientErrorException.class);
        assertThrows(ExternalServiceConnectionFailedException.class, () -> service.getCurrentSpeed());
        assertThrows(ExternalServiceConnectionFailedException.class, () -> service.getCurrentSpeed());
    }

    @Test
    void getDistanceBetweenUserLocationAndIss() {
        //given
        LocationDto userLocation = LocationDto.builder()
                .latitude(42.443087)
                .longitude(76.488707)
                .time(1552749164L)
                .build();
        IPLocationData ipLocationDataStub = IPLocationData.builder()
                .cityName("Wrocław")
                .country("Poland")
                .latitude("42.443087")
                .longitude("76.488707")
                .build();
        GeoCoordinates coordinates = GeoCoordinates.builder()
                .latitude("40.366633")
                .longitude("74.640832")
                .build();
        ISSLocation issLocation = ISSLocation.builder()
                .issPosition(coordinates)
                .message("success")
                .timestamp(1552749164L)
                .build();

        UserLocationResult expected = UserLocationResult.builder()
                .latitude("42.443087")
                .longitude("76.488707")
                .distance(Double.valueOf(Math.round(277.5)))
                .build();
        when(fetchService.getLocationDataFromIP(Mockito.anyString())).thenReturn(ipLocationDataStub);
        when(fetchService.getCurrentLocation()).thenReturn(issLocation);
        //when
        UserLocationResult actual = service.getDistanceBetweenUserLocationAndIss("178.43.255.43");

        //then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.getLatitude(), actual.getLatitude()),
                () -> assertEquals(expected.getLongitude(), actual.getLongitude()),
                () -> assertEquals(expected.getDistance(), actual.getDistance(), 0.1)
        );

    }

    @Test
    void getTotalDistance() {
        //Todo create service method TEST!!!
    }

    @Test
    void getPredictedPassOverLocation() {
        //Todo create service method TEST!!!
    }

    @Test
    void getDistanceFromGivenLocation() {
        //Todo create service method TEST!!!
    }

    @Test
    void getPredictedPassesFromGivenLocation() {
        //Todo create service method TEST!!!
    }
}