package tj.javadeveloper.issspyapp.service.locationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import tj.javadeveloper.issspyapp.commons.exceptions.ExternalServiceConnectionFailedException;
import tj.javadeveloper.issspyapp.commons.exceptions.InternalServerCustomException;
import tj.javadeveloper.issspyapp.commons.utils.LocationUtils;
import tj.javadeveloper.issspyapp.domain.dto.LocationDto;
import tj.javadeveloper.issspyapp.domain.dto.PredictedPassDto;
import tj.javadeveloper.issspyapp.domain.dto.UserLocationResult;
import tj.javadeveloper.issspyapp.domain.entity.LocationEntity;
import tj.javadeveloper.issspyapp.domain.resttempalte.IPLocationData;
import tj.javadeveloper.issspyapp.domain.resttempalte.ISSLocation;
import tj.javadeveloper.issspyapp.domain.resttempalte.IssPredictedPass;
import tj.javadeveloper.issspyapp.mapper.LocationMapper;
import tj.javadeveloper.issspyapp.mapper.PredictedPassMapper;
import tj.javadeveloper.issspyapp.repository.LocationEntityRepository;
import tj.javadeveloper.issspyapp.service.resttemplateservice.RestTemplateFetchService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static tj.javadeveloper.issspyapp.commons.utils.LocationUtils.distanceInKm;

@Service
public class LocationServiceImpl implements LocationService {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final RestTemplateFetchService fetchService;
    private final LocationMapper locationMapper;
    private final LocationEntityRepository locationRepository;
    private final PredictedPassMapper predictedPassMapper;

    public LocationServiceImpl(RestTemplateFetchService fetchService, LocationMapper locationMapper,
                               LocationEntityRepository locationRepository, PredictedPassMapper predictedPassMapper) {
        this.fetchService = fetchService;
        this.locationMapper = locationMapper;
        this.locationRepository = locationRepository;
        this.predictedPassMapper = predictedPassMapper;
    }

    @Autowired
    public LocationDto getCurrentLocation() {
        ISSLocation issLocation;
        try {
            issLocation = fetchService.getCurrentLocation();
        } catch (Exception e) {
            String message = "Cannot connect to external source";
            logger.log(Level.WARNING, message);
            throw new ExternalServiceConnectionFailedException(message);
        }
        return locationMapper.toLocationDto(issLocation);
    }

    public Double getCurrentSpeed() {
        ISSLocation issLocation1;
        ISSLocation issLocation2;
        try {
            issLocation1 = fetchService.getCurrentLocation();
            Thread.sleep(3000);
            issLocation2 = fetchService.getCurrentLocation();
        } catch (InterruptedException e) {
            logger.log(Level.WARNING, "InterruptedException has been thrown IN getCurrentSpeed() Method");
            throw new InternalServerCustomException();
        } catch (Exception e) {
            String message = "Cannot connect to external source";
            logger.log(Level.WARNING, message);
            throw new ExternalServiceConnectionFailedException(message);
        }

        LocationDto location1 = locationMapper.toLocationDto(issLocation1);
        LocationDto location2 = locationMapper.toLocationDto(issLocation2);

        double currentSpeed = LocationUtils.calculateCurrentSpeedInKmPerHour(location1, location2);
        return currentSpeed;
    }

    public UserLocationResult getDistanceBetweenUserLocationAndIss(String ipAddress) {
        UserLocationResult result;
        try {
            IPLocationData userLocation = fetchService.getLocationDataFromIP(ipAddress);
            LocationDto issCurrentLocation = locationMapper.toLocationDto(fetchService.getCurrentLocation());
            LocationDto userCoordinates = LocationDto.builder()
                    .latitude(Double.parseDouble(userLocation.getLatitude()))
                    .longitude(Double.parseDouble(userLocation.getLongitude()))
                    .build();

            double distance = Math.round(distanceInKm(issCurrentLocation, userCoordinates));

            result = UserLocationResult.builder()
                    .country(userLocation.getCountry())
                    .locationName(userLocation.getCityName())
                    .distance(distance)
                    .latitude(userLocation.getLatitude())
                    .longitude(userLocation.getLongitude())
                    .time(issCurrentLocation.getTime())
                    .build();
        } catch (HttpClientErrorException e) {
            String message = "Invalid external source address";
            logger.log(Level.WARNING, message);
            throw new ExternalServiceConnectionFailedException(message);
        } catch (ResourceAccessException e) {
            String message = "Cannot connect to external source";
            logger.log(Level.WARNING, message);
            throw new ExternalServiceConnectionFailedException(message);
        } catch (Exception e) {
            String message = "External server error, pleas check data or try agian later";
            logger.log(Level.WARNING, message);
            throw new ExternalServiceConnectionFailedException(message);
        }
        return result;
    }

    public Double getTotalDistance() {
        List<LocationDto> issPositions = locationDtoList(locationRepository.findAll());
        Double distance = LocationUtils.calculateTotalDistanceInKm(issPositions);
        return distance;
    }

    public PredictedPassDto getPredictedPassOverLocation(String ipAddress) {
        PredictedPassDto passesDto;
        try {
            IPLocationData userLocation = fetchService.getLocationDataFromIP(ipAddress);
            IssPredictedPass issPredictedPass = fetchService.getPredictedPassFromCoordinates(ipAddress);
            passesDto = predictedPassMapper.toPredictedPassDto(issPredictedPass);

        } catch (HttpClientErrorException e) {
            String message = "Invalid external source address";
            logger.log(Level.WARNING, message);
            throw new ExternalServiceConnectionFailedException(message);
        } catch (ResourceAccessException e) {
            String message = "Cannot connect to external source";
            logger.log(Level.WARNING, message);
            throw new ExternalServiceConnectionFailedException(message);
        } catch (Exception e) {
            String message = "External server error, pleas check data or try agian later";
            logger.log(Level.WARNING, message);
            throw new ExternalServiceConnectionFailedException(message);
        }
        return passesDto;
    }

    public UserLocationResult getDistanceFromGivenLocation(String lat, String latDir, String lon, String lonDir) {
        UserLocationResult result;
        try {
            LocationDto issCurrentLocation = locationMapper.toLocationDto(fetchService.getCurrentLocation());
            LocationDto givenLocation = LocationUtils.convertDataToLocationDto(lat, latDir, lon, lonDir);
            double distance = Math.round(distanceInKm(issCurrentLocation, givenLocation));

            result = UserLocationResult.builder()
                    .latitude(givenLocation.getLatitude() + "")
                    .longitude(givenLocation.getLongitude() + "")
                    .distance(distance)
                    .time(issCurrentLocation.getTime())
                    .build();
        } catch (HttpClientErrorException | ResourceAccessException e) {
            String message = "Cannot connect to external source";
            logger.log(Level.WARNING, message);
            throw new ExternalServiceConnectionFailedException(message);
        } catch (Exception e) {
            String message = "External server error, pleas check data or try agian later";
            logger.log(Level.WARNING, message);
            throw new ExternalServiceConnectionFailedException(message);
        }
        return result;
    }

    public PredictedPassDto getPredictedPassesFromGivenLocation(String lat, String latDir, String lon, String lonDir) {
        PredictedPassDto passesDto;
        try {
            LocationDto locationDto = LocationUtils.convertDataToLocationDto(lat, latDir, lon, lonDir);
            IssPredictedPass issPredictedPass = fetchService.predictedPassesRestTemplate(locationDto.getLatitude().toString(),
                    locationDto.getLongitude().toString());
            passesDto = predictedPassMapper.toPredictedPassDto(issPredictedPass);
        } catch (HttpClientErrorException e) {
            String message = "Invalid external source address";
            logger.log(Level.WARNING, message);
            throw new ExternalServiceConnectionFailedException(message);
        } catch (ResourceAccessException e) {
            String message = "Cannot connect to external source";
            logger.log(Level.WARNING, message);
            throw new ExternalServiceConnectionFailedException(message);
        } catch (HttpServerErrorException e) {
            String message = "External server error, please check inserted coordinates";
            logger.log(Level.WARNING, message);
            throw new ExternalServiceConnectionFailedException(message);
        } catch (Exception e) {
            String message = "External server error, pleas check data or try agian later";
            logger.log(Level.WARNING, message);
            throw new ExternalServiceConnectionFailedException(message);
        }

        return passesDto;
    }

    private List<LocationDto> locationDtoList(List<LocationEntity> list) {
        return list.stream().map(locationMapper::fromLocationEntityToLocationDto)
                .sorted()
                .collect(Collectors.toList());

    }
}
