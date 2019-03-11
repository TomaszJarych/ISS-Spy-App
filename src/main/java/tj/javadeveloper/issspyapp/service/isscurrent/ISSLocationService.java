package tj.javadeveloper.issspyapp.service.isscurrent;

import tj.javadeveloper.issspyapp.domain.isslocation.ISSLocation;

public interface ISSLocationService {

    public ISSLocation getCurrentLocation();

    public String getPredictedOverheadPasses(String ipAddress);
}
