package tj.javadeveloper.issspyapp.controller.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tj.javadeveloper.issspyapp.domain.iplocation.IssPassData;
import tj.javadeveloper.issspyapp.domain.iplocation.IssPredictedPass;
import tj.javadeveloper.issspyapp.domain.isslocation.ISSLocation;
import tj.javadeveloper.issspyapp.service.isscurrent.ISSLocationServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static tj.javadeveloper.issspyapp.commons.utils.Constants.REST_API_DEFAULT_PATH;

@RestController
@RequestMapping(path = REST_API_DEFAULT_PATH + "/iss")
public class ISSRestController {

    private final ISSLocationServiceImpl currentLocationService;

    @Autowired
    public ISSRestController(ISSLocationServiceImpl currentLocationService) {
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

    @GetMapping(path = "/predict")
    public ResponseEntity getPredictedPassesOverUsersLocation(HttpServletRequest servletRequest) {
        String remoteAddr = "";
        if (servletRequest != null) {
            remoteAddr = servletRequest.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = servletRequest.getRemoteAddr();
            }
        }
        IssPredictedPass predictedPasses
                = currentLocationService.getPredictedOverheadPasses(remoteAddr);


        List<IssPassData> list = predictedPasses.getPassesList();
        Collections.sort(list);
        list.stream().forEach(el -> {
            LocalDateTime date = LocalDateTime
                    .ofInstant(Instant.ofEpochSecond(el.getRisetime()), ZoneId.systemDefault());
            System.out.println(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        });

        return ResponseEntity.ok(predictedPasses);
    }
}
