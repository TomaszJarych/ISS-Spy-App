package tj.javadeveloper.issspyapp.service.isscurrent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tj.javadeveloper.issspyapp.domain.ISSLocation;

import static tj.javadeveloper.issspyapp.commons.utils.Constants.OPEN_NOTIFY_ISS_CURRENT_LOCATION_URL;

@Service
public class IssCurrentLocationService implements CurrentLocationService {


    private final RestTemplate restTemplate;

    @Autowired
    public IssCurrentLocationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ISSLocation getCurrentLocation() {
        ISSLocation currentLocation =
                restTemplate.getForObject(OPEN_NOTIFY_ISS_CURRENT_LOCATION_URL, ISSLocation.class);
        return currentLocation;
    }
}
