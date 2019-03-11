package tj.javadeveloper.issspyapp.service.isscurrent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tj.javadeveloper.issspyapp.commons.utils.Constants;
import tj.javadeveloper.issspyapp.domain.iplocation.IPLocationData;
import tj.javadeveloper.issspyapp.domain.isslocation.ISSLocation;

import static tj.javadeveloper.issspyapp.commons.utils.Constants.LOCATION_FROM_IP_URL;
import static tj.javadeveloper.issspyapp.commons.utils.Constants.OPEN_NOTIFY_ISS_CURRENT_LOCATION_URL;

@Service
public class ISSLocationServiceImpl implements ISSLocationService {


    private final RestTemplate restTemplate;

    @Autowired
    public ISSLocationServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ISSLocation getCurrentLocation() {
        ISSLocation currentLocation =
                restTemplate.getForObject(OPEN_NOTIFY_ISS_CURRENT_LOCATION_URL, ISSLocation.class);
        return currentLocation;
    }

    public String getPredictedOverheadPasses(String ipAddress) {
        String ip = "178.43.255.43";
        IPLocationData locationData = restTemplate
                .getForObject(LOCATION_FROM_IP_URL + ip, IPLocationData.class);
        String data = predictedPassesRestTemplate(locationData.getLatitude(), locationData.getLongitude());
        System.out.println(data);
        return "";

    }

    private String predictedPassesRestTemplate(String latitude, String longitude) {
        ResponseEntity<String> ISSData = restTemplate
                .getForEntity(Constants.getISSPredictedPassesURL(latitude, longitude), String.class);
        return ISSData.getBody();
    }
}
