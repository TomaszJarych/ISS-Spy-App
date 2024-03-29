package tj.javadeveloper.issspyapp.controller.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import tj.javadeveloper.issspyapp.commons.exceptions.ExternalServiceConnectionFailedException;
import tj.javadeveloper.issspyapp.commons.exceptions.InternalServerCustomException;
import tj.javadeveloper.issspyapp.commons.exceptions.InvalidLocationDataException;
import tj.javadeveloper.issspyapp.domain.ResultWrapper;

@ControllerAdvice(basePackages = {"tj.javadeveloper.issspyapp.controller.restcontroller"})
@ResponseBody
public class GlobalRestExceptionHandler {

    @ExceptionHandler(ExternalServiceConnectionFailedException.class)
    public ResponseEntity externalServiceConnectionFailedExceptionHandler(
            ExternalServiceConnectionFailedException exc) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ResultWrapper.error("failed", exc.getMessage()));
    }

    @ExceptionHandler(InternalServerCustomException.class)
    public ResponseEntity internalServerCustomExceptionHandler(InternalServerCustomException exc) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResultWrapper.error("failed", exc.getMessage()));
    }

    @ExceptionHandler(InvalidLocationDataException.class)
    public ResponseEntity invalidLocationDataExceptionHandler(InvalidLocationDataException exc) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResultWrapper.error("failed", exc.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity missingServletRequestParameterException(MissingServletRequestParameterException exc) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResultWrapper.error("failed", "Missing required request parameter"));
    }
}
