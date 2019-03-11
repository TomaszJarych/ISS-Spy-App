package tj.javadeveloper.issspyapp.service.isscurrent;

import tj.javadeveloper.issspyapp.domain.iplocation.IssPredictedPass;
import tj.javadeveloper.issspyapp.domain.isslocation.ISSLocation;

public interface ISSLocationService {

    public ISSLocation getCurrentLocation();

    public IssPredictedPass getPredictedOverheadPasses(String ipAddress);
}
