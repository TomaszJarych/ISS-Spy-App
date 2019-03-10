package tj.javadeveloper.issspyapp.controller.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tj.javadeveloper.issspyapp.domain.ISSLocation;
import tj.javadeveloper.issspyapp.service.isscurrent.IssCurrentLocationService;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static tj.javadeveloper.issspyapp.commons.utils.Constants.REST_API_DEFAULT_PATH;

@RestController
@RequestMapping(path = REST_API_DEFAULT_PATH + "/iss")
public class ISSRestController {

    private final IssCurrentLocationService currentLocationService;

    @Autowired
    public ISSRestController(IssCurrentLocationService currentLocationService) {
        this.currentLocationService = currentLocationService;
    }

    @GetMapping(path = "/hello", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity helloWorld(HttpServletRequest request) {
        return ResponseEntity.ok("Hello world FROM " + request.getRemoteAddr());
    }

    @GetMapping(path = "/current", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getCurrentLocation() {
        ISSLocation current = currentLocationService.getCurrentLocation();
        return ResponseEntity.ok(current);
    }
}
