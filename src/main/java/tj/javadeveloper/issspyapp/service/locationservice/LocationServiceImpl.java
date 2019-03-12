package tj.javadeveloper.issspyapp.service.locationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tj.javadeveloper.issspyapp.domain.dto.LocationDto;
import tj.javadeveloper.issspyapp.domain.resttempalte.ISSLocation;
import tj.javadeveloper.issspyapp.mapper.LocationMapper;
import tj.javadeveloper.issspyapp.repository.LocationRepository;
import tj.javadeveloper.issspyapp.service.resttemplateservice.RestTemplateFetchService;

@Service
public class LocationServiceImpl implements LocationService {

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
}
