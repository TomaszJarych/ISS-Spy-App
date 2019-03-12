package tj.javadeveloper.issspyapp.service.resttemplateservice;

import tj.javadeveloper.issspyapp.domain.resttempalte.ISSLocation;
import tj.javadeveloper.issspyapp.domain.resttempalte.IssPredictedPass;


public interface RestTemplateFetchService {

    ISSLocation getCurrentLocation();

    IssPredictedPass getPredictedPassFromCoordinates(String ipAddress);

}
