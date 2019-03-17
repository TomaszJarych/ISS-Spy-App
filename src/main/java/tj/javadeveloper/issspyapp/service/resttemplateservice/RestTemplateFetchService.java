package tj.javadeveloper.issspyapp.service.resttemplateservice;

import tj.javadeveloper.issspyapp.domain.resttempalte.IPLocationData;
import tj.javadeveloper.issspyapp.domain.resttempalte.ISSLocation;
import tj.javadeveloper.issspyapp.domain.resttempalte.IssPredictedPass;


public interface RestTemplateFetchService {

    ISSLocation getCurrentLocation();

    IPLocationData getLocationDataFromIP(String ipAddress);

    IssPredictedPass getPredictedPassFromCoordinates(String ipAddress);

    IssPredictedPass predictedPassesRestTemplate(String latitude, String longitude);

}
