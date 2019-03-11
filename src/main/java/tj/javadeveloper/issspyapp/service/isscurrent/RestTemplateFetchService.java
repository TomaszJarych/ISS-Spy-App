package tj.javadeveloper.issspyapp.service.isscurrent;

import tj.javadeveloper.issspyapp.domain.resttempalte.ISSLocation;
import tj.javadeveloper.issspyapp.domain.resttempalte.IssPredictedPass;


public interface RestTemplateFetchService {

    public ISSLocation getCurrentLocation();

    public IssPredictedPass getPredictedPassFromCoordinates(String ipAddress);

}
