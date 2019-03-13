package tj.javadeveloper.issspyapp.service.locationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tj.javadeveloper.issspyapp.commons.utils.LocationUtils;
import tj.javadeveloper.issspyapp.domain.dto.LocationDto;
import tj.javadeveloper.issspyapp.domain.dto.UserLocationResult;
import tj.javadeveloper.issspyapp.domain.resttempalte.IPLocationData;
import tj.javadeveloper.issspyapp.domain.resttempalte.ISSLocation;
import tj.javadeveloper.issspyapp.mapper.LocationMapper;
import tj.javadeveloper.issspyapp.repository.LocationRepository;
import tj.javadeveloper.issspyapp.service.resttemplateservice.RestTemplateFetchService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static tj.javadeveloper.issspyapp.commons.utils.LocationUtils.distanceInKm;

@Service
public class LocationServiceImpl implements LocationService {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final RestTemplateFetchService fetchService;
    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(RestTemplateFetchService fetchService, LocationMapper locationMapper,
                               LocationRepository locationRepository) {
        this.fetchService = fetchService;
        this.locationMapper = locationMapper;
        this.locationRepository = locationRepository;
    }

    public LocationDto getCurrentLocation() {
        ISSLocation issLocation = fetchService.getCurrentLocation();
        return locationMapper.toLocationDto(issLocation);
    }

    public Double getCurrentSpeed() {
        ISSLocation issLocation1;
        ISSLocation issLocation2;
        try {
            issLocation1 = fetchService.getCurrentLocation();
            Thread.sleep(5000);
            issLocation2 = fetchService.getCurrentLocation();
        } catch (InterruptedException e) {
            logger.log(Level.WARNING, "InterruptedException has been thrown");
            issLocation1 = new ISSLocation();
            issLocation2 = new ISSLocation();
            //TODO throw custom exception
        }
        LocationDto location1 = locationMapper.toLocationDto(issLocation1);
        LocationDto location2 = locationMapper.toLocationDto(issLocation2);

        double currentSpeed = LocationUtils.calculateCurrentSpeedInKmPerHour(location1, location2);
        return currentSpeed;
    }

    public UserLocationResult getDistanceBetweenUserLocationAndIss(String ipAddress) {
        IPLocationData userLocation = fetchService.getLocationDataFromIP(ipAddress);
        LocationDto issCurrentLocation = locationMapper.toLocationDto(fetchService.getCurrentLocation());
        LocationDto userCoordinates = LocationDto.builder()
                .latitude(Double.parseDouble(userLocation.getLatitude()))
                .longitude(Double.parseDouble(userLocation.getLongitude()))
                .build();

        double distance = Math.round(distanceInKm(issCurrentLocation, userCoordinates));

        UserLocationResult result = UserLocationResult.builder()
                .country(userLocation.getCountry())
                .locationName(userLocation.getCityName())
                .distance(distance)
                .latitude(userLocation.getLatitude())
                .longitude(userLocation.getLongitude())
                .time(issCurrentLocation.getTime())
                .build();
        return result;
    }

    public Double getTotalDistance() {
        List<LocationDto> issPositions = locationRepository.findAll();
        Double distance = LocationUtils.calculateTotalDistanceInKm(issPositions);
        return distance;
    }
}
