package tj.javadeveloper.issspyapp.controller.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tj.javadeveloper.issspyapp.commons.utils.LocationUtils;
import tj.javadeveloper.issspyapp.domain.ResultWrapper;
import tj.javadeveloper.issspyapp.domain.dto.*;
import tj.javadeveloper.issspyapp.service.locationservice.LocationService;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static tj.javadeveloper.issspyapp.commons.utils.Constants.REST_API_DEFAULT_PATH;

@RestController
@RequestMapping(path = REST_API_DEFAULT_PATH + "/iss")
public class ISSRestController {

    private final LocationService locationService;

    @Autowired
    public ISSRestController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping(path = "/current", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getCurrentLocation() {
        LocationDto dto = locationService.getCurrentLocation();

        return ResponseEntity.ok(ResultWrapper.ok(dto));
    }

    @GetMapping(path = "/predict", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getPredictedPassesOverUsersLocation(HttpServletRequest servletRequest) {
        String remoteAddr1 = LocationUtils.getIPFromRequest(servletRequest);
        PredictedPassDto passesDto = locationService.getPredictedPassOverLocation(remoteAddr1);
        return ResponseEntity.ok(ResultWrapper.ok(passesDto));
    }


    @GetMapping(path = "/speed", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getCurrentIssSpeed() {
        return ResponseEntity.ok(ResultWrapper.ok(new CurrentSpeedResult(locationService.getCurrentSpeed())));

    }

    @GetMapping(path = "/distanceFromUser", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getDistanceFromIssAndUser(HttpServletRequest servletRequest) {
        String ipAddres = LocationUtils.getIPFromRequest(servletRequest);
        UserLocationResult userLocationResult =
                locationService.getDistanceBetweenUserLocationAndIss(ipAddres);

        return ResponseEntity.ok(ResultWrapper.ok(userLocationResult));
    }

    @GetMapping(path = "/totalDistance", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getTotalDistance() {
        long distance = Math.round(locationService.getTotalDistance());
        return ResponseEntity.ok(ResultWrapper.ok(new TotalDistanceResult(distance)));
    }

    @GetMapping(path = "/distanceFromLocation", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getDistanceFromGivenCoorinates
            (@RequestParam(name = "lat", required = true) String lat,
             @RequestParam(name = "latDir", required = true) String latDir,
             @RequestParam(name = "lon", required = true) String lon,
             @RequestParam(name = "lonDir") String lonDir) {
        UserLocationResult result = locationService.getDistanceFromGivenLocation(lat, latDir, lon, lonDir);
        return ResponseEntity.ok(ResultWrapper.ok(result));
    }

    @GetMapping(path = "/predictFromCoordinates", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getOverheadPassesFromGivenCoorinates
            (@RequestParam(name = "lat", required = true) String lat,
             @RequestParam(name = "latDir", required = true) String latDir,
             @RequestParam(name = "lon", required = true) String lon,
             @RequestParam(name = "lonDir") String lonDir) {
        PredictedPassDto passesDto = locationService.getPredictedPassesFromGivenLocation(lat, latDir, lon, lonDir);
        return ResponseEntity.ok(ResultWrapper.ok(passesDto));
    }
}
