package tj.javadeveloper.issspyapp.service.resttemplateservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import tj.javadeveloper.issspyapp.commons.utils.Constants;
import tj.javadeveloper.issspyapp.domain.resttempalte.IPLocationData;
import tj.javadeveloper.issspyapp.domain.resttempalte.ISSLocation;
import tj.javadeveloper.issspyapp.domain.resttempalte.IssCrewData;
import tj.javadeveloper.issspyapp.domain.resttempalte.IssPredictedPass;

import static tj.javadeveloper.issspyapp.commons.utils.Constants.*;

@Service
public class RestTemplateFetchServiceImpl implements RestTemplateFetchService {


    private final RestTemplate restTemplate;

    @Autowired
    public RestTemplateFetchServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ISSLocation getCurrentLocation() throws HttpClientErrorException, ResourceAccessException {
        ISSLocation currentLocation =
                restTemplate.getForObject(OPEN_NOTIFY_ISS_CURRENT_LOCATION_URL, ISSLocation.class);
        return currentLocation;
    }

    public IssPredictedPass getPredictedPassFromCoordinates(String ipAddress) throws HttpClientErrorException, ResourceAccessException {
        IPLocationData locationData = getLocationDataFromIP(ipAddress);
        String userLatitude = locationData.getLatitude();
        String userLongitude = locationData.getLongitude();
        IssPredictedPass issPredictedPass = predictedPassesRestTemplate(userLatitude, userLongitude);
        return issPredictedPass;
    }

    public IssCrewData getISSCrewData() throws HttpClientErrorException {
        ResponseEntity<IssCrewData> crewDataResponseEntity =
                restTemplate.getForEntity(OPEN_NOTIFY_ISS_CURRENT_CREW_URL, IssCrewData.class);
        return crewDataResponseEntity.getBody();
    }

    public IPLocationData getLocationDataFromIP(String ipAddress) throws HttpClientErrorException, ResourceAccessException {
        IPLocationData locationData = restTemplate
                .getForObject(LOCATION_FROM_IP_URL + ipAddress, IPLocationData.class);
        return locationData;
    }

    public IssPredictedPass predictedPassesRestTemplate(String latitude, String longitude) throws HttpClientErrorException, ResourceAccessException {
        ResponseEntity<IssPredictedPass> ISSData = restTemplate
                .getForEntity(Constants.getISSPredictedPassesURL(latitude, longitude), IssPredictedPass.class);
        return ISSData.getBody();
    }
}
