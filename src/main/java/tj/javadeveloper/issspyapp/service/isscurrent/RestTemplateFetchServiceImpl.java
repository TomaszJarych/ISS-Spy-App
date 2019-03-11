package tj.javadeveloper.issspyapp.service.isscurrent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tj.javadeveloper.issspyapp.commons.utils.Constants;
import tj.javadeveloper.issspyapp.domain.resttempalte.IPLocationData;
import tj.javadeveloper.issspyapp.domain.resttempalte.ISSLocation;
import tj.javadeveloper.issspyapp.domain.resttempalte.IssPredictedPass;

import static tj.javadeveloper.issspyapp.commons.utils.Constants.LOCATION_FROM_IP_URL;
import static tj.javadeveloper.issspyapp.commons.utils.Constants.OPEN_NOTIFY_ISS_CURRENT_LOCATION_URL;

@Service
public class RestTemplateFetchServiceImpl implements RestTemplateFetchService {


    private final RestTemplate restTemplate;

    @Autowired
    public RestTemplateFetchServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ISSLocation getCurrentLocation() {
        ISSLocation currentLocation =
                restTemplate.getForObject(OPEN_NOTIFY_ISS_CURRENT_LOCATION_URL, ISSLocation.class);
        return currentLocation;
    }

    public IssPredictedPass getPredictedPassFromCoordinates(String ipAddress) {
        IPLocationData locationData = getLocationDataFromIP(ipAddress);
        String userLatitude = locationData.getLatitude();
        String userLongitude = locationData.getLongitude();
        IssPredictedPass issPredictedPass = predictedPassesRestTemplate(userLatitude, userLongitude);
        return issPredictedPass;
    }

    private IPLocationData getLocationDataFromIP(String ipAddress) {
        String ip = "178.43.255.43";
        IPLocationData locationData = restTemplate
                .getForObject(LOCATION_FROM_IP_URL + ip, IPLocationData.class);
        return locationData;
    }

    private IssPredictedPass predictedPassesRestTemplate(String latitude, String longitude) {
        ResponseEntity<IssPredictedPass> ISSData = restTemplate
                .getForEntity(Constants.getISSPredictedPassesURL(latitude, longitude), IssPredictedPass.class);
        return ISSData.getBody();
    }
}