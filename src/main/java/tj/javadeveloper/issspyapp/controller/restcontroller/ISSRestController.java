package tj.javadeveloper.issspyapp.controller.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tj.javadeveloper.issspyapp.domain.dto.LocationDto;
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

    @GetMapping(path = "/hello", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity helloWorld(HttpServletRequest request) {
        return ResponseEntity.ok("Hello world FROM " + request.getRemoteAddr());
    }

    @GetMapping(path = "/current", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getCurrentLocation() {
        LocationDto dto = locationService.getCurrentLocation();

        return ResponseEntity.ok(dto);
    }

    @GetMapping(path = "/predict", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getPredictedPassesOverUsersLocation(HttpServletRequest servletRequest) {
        String remoteAddr = "";
        if (servletRequest != null) {
            remoteAddr = servletRequest.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "" .equals(remoteAddr)) {
                remoteAddr = servletRequest.getRemoteAddr();
            }
        }
        //TODO complete this stub method!!!!
        return ResponseEntity.ok("OK");
    }

    @GetMapping(path = "/speed", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getCurrentIssSpeed() {
        return ResponseEntity.ok("{\"currentSpeedInKmPerHour\" : " + locationService.getCurrentSpeed() + " }");

    }
}
