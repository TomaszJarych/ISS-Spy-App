package tj.javadeveloper.issspyapp.service.locationservice;

import tj.javadeveloper.issspyapp.domain.dto.LocationDto;
import tj.javadeveloper.issspyapp.domain.dto.PredictedPassDto;
import tj.javadeveloper.issspyapp.domain.dto.UserLocationResult;

public interface LocationService {

    LocationDto getCurrentLocation();

    Double getCurrentSpeed();

    UserLocationResult getDistanceBetweenUserLocationAndIss(String ipAddress);

    Double getTotalDistance();

    PredictedPassDto getPredictedPassOverLocation(String ipAddress);
}
