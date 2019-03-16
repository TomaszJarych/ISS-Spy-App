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
import tj.javadeveloper.issspyapp.domain.dto.PredictedPassDataDto;
import tj.javadeveloper.issspyapp.domain.dto.PredictedPassDto;
import tj.javadeveloper.issspyapp.domain.dto.UserLocationResult;
import tj.javadeveloper.issspyapp.domain.entity.LocationEntity;
import tj.javadeveloper.issspyapp.domain.resttempalte.*;
import tj.javadeveloper.issspyapp.mapper.LocationMapper;
import tj.javadeveloper.issspyapp.mapper.PredictedPassMapper;
import tj.javadeveloper.issspyapp.repository.LocationEntityRepository;
import tj.javadeveloper.issspyapp.service.resttemplateservice.RestTemplateFetchService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {
    @Mock
    private RestTemplateFetchService fetchService;
    @Mock
    private LocationEntityRepository locationRepository;

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
                .distance(278.0)
                .build();
        when(fetchService.getLocationDataFromIP(anyString())).thenReturn(ipLocationDataStub);
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
    void getDistanceBetweenUserLocationAndIssShouldThrowsExceptions() {
        //when
        when(fetchService.getLocationDataFromIP(anyString()))
                .thenThrow(ResourceAccessException.class, HttpClientErrorException.class);
        //then
        assertAll(
                () -> assertThrows(ExternalServiceConnectionFailedException.class,
                        () -> service.getDistanceBetweenUserLocationAndIss("178.45.255.44")),
                () -> assertThrows(ExternalServiceConnectionFailedException.class,
                        () -> service.getDistanceBetweenUserLocationAndIss("178.45.255.44")));

    }

    @Test
    void getTotalDistance() {
        //given
        LocationEntity location1 = LocationEntity.builder()
                .latitude(40.366633)
                .longitude(74.640832)
                .time(1L)
                .build();
        LocationEntity location2 = LocationEntity.builder()
                .latitude(42.443087)
                .longitude(76.488707)
                .time(2L)
                .build();
        double expected = 277.5;
        List<LocationEntity> listStub = Arrays.asList(location1, location2);
        when(locationRepository.findAll()).thenReturn(listStub);

        //when

        Double actual = service.getTotalDistance();

        //then
        assertNotNull(actual);
        assertEquals(expected, actual, 1);
        verify(locationRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getPredictedPassOverLocation() {
        //given
        Long altitude = 100L;
        Double latitude = 51.1607;
        Double longitude = 17.1338;
        Integer passes = 5;
        Integer duration1 = 461;
        Long risetime1 = 1552595791L;
        Integer duration2 = 623;
        Long risetime2 = 1552601456L;
        Integer duration3 = 645;
        Long risetime3 = 1552607228L;
        Integer duration4 = 645;
        Long risetime4 = 1552613025L;
        Integer duration5 = 621;
        Long risetime5 = 1552618819L;
        Long requestTime = 1552581150L;

        IPLocationData ipLocationDataStub = IPLocationData.builder()
                .cityName("Wrocław")
                .country("Poland")
                .latitude("42.443087")
                .longitude("76.488707")
                .build();
        IssPassData passData1 = IssPassData.builder().duration(duration1).risetime(risetime1).build();
        IssPassData passData2 = IssPassData.builder().duration(duration2).risetime(risetime2).build();
        IssPassData passData3 = IssPassData.builder().duration(duration3).risetime(risetime3).build();
        IssPassData passData4 = IssPassData.builder().duration(duration4).risetime(risetime4).build();
        IssPassData passData5 = IssPassData.builder().duration(duration5).risetime(risetime5).build();
        List<IssPassData> issPassDataList = Arrays.asList(passData1, passData2, passData3, passData4, passData5);

        IssPassRequestData issPassRequestData = IssPassRequestData.builder()
                .altitude(altitude)
                .requestTime(requestTime)
                .latitude(latitude)
                .longitude(longitude)
                .passes(passes)
                .build();

        IssPredictedPass issPredictedPassStub = IssPredictedPass.builder()
                .message("success")
                .requestData(issPassRequestData)
                .passesList(issPassDataList)
                .build();
        PredictedPassDataDto passDataDto1 = PredictedPassDataDto.builder().duration(duration1)
                .risetime(predictedPassMapper.convertTimeStampToLocalDateTime(risetime1)).build();
        PredictedPassDataDto passDataDto2 = PredictedPassDataDto.builder().duration(duration2)
                .risetime(predictedPassMapper.convertTimeStampToLocalDateTime(risetime2)).build();
        PredictedPassDataDto passDataDto3 = PredictedPassDataDto.builder().duration(duration3)
                .risetime(predictedPassMapper.convertTimeStampToLocalDateTime(risetime3)).build();
        PredictedPassDataDto passDataDto4 = PredictedPassDataDto.builder().duration(duration4)
                .risetime(predictedPassMapper.convertTimeStampToLocalDateTime(risetime4)).build();
        PredictedPassDataDto passDataDto5 = PredictedPassDataDto.builder().duration(duration5)
                .risetime(predictedPassMapper.convertTimeStampToLocalDateTime(risetime5)).build();

        List<PredictedPassDataDto> predictedPassDataDtoList = Arrays.asList(passDataDto1, passDataDto2, passDataDto3,
                passDataDto4, passDataDto5);
        PredictedPassDto expected = PredictedPassDto.builder()
                .latitude(latitude)
                .longitude(longitude)
                .passesNumber(passes)
                .passesData(predictedPassDataDtoList)
                .build();

        when(fetchService.getLocationDataFromIP(anyString())).thenReturn(ipLocationDataStub);
        when(fetchService.getPredictedPassFromCoordinates(anyString())).thenReturn(issPredictedPassStub);

        //when
        PredictedPassDto actual = service.getPredictedPassOverLocation("178.45.255.44");

        //then

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.getLongitude(), actual.getLongitude()),
                () -> assertEquals(expected.getLatitude(), actual.getLatitude()),
                () -> assertEquals(expected.getPassesNumber(), actual.getPassesNumber()),
                () -> assertEquals(expected.getPassesData().size(), actual.getPassesData().size())
        );
        verify(fetchService, Mockito.atLeastOnce()).getLocationDataFromIP(anyString());
        verify(fetchService, Mockito.atLeastOnce()).getPredictedPassFromCoordinates(anyString());


    }
}