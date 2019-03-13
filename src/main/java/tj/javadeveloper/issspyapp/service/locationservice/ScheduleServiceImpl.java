package tj.javadeveloper.issspyapp.service.locationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import tj.javadeveloper.issspyapp.domain.dto.LocationDto;
import tj.javadeveloper.issspyapp.domain.resttempalte.ISSLocation;
import tj.javadeveloper.issspyapp.mapper.LocationMapper;
import tj.javadeveloper.issspyapp.repository.LocationRepository;
import tj.javadeveloper.issspyapp.service.resttemplateservice.RestTemplateFetchService;

import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class ScheduleServiceImpl implements ScheduleService {

    private RestTemplateFetchService fetchService;
    private LocationRepository locationRepository;
    private LocationMapper locationMapper;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    public ScheduleServiceImpl(RestTemplateFetchService fetchService,
                               LocationRepository locationRepository, LocationMapper locationMapper) {
        this.fetchService = fetchService;
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void fetchDataFromOpenNotify() {
        //Todo change CRON EXPRESSION TO 1/Minute
        try {
            ISSLocation issLocation = fetchService.getCurrentLocation();
            LocationDto dto = locationMapper.toLocationDto(issLocation);
            locationRepository.saveLocation(dto);
            logger.log(Level.INFO, "ISS data has been received from external service");
        } catch (HttpClientErrorException | ResourceAccessException exception) {
            logger.log(Level.WARNING,
                    "Cannont connect to external source. Data has not been received");
        }
    }
}
