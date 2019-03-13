package tj.javadeveloper.issspyapp.commons.exceptions;

public class ExternalServiceConnectionFailedException extends RuntimeException {

    public ExternalServiceConnectionFailedException() {
    }

    public ExternalServiceConnectionFailedException(String message) {
        super(message);
    }

    public ExternalServiceConnectionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExternalServiceConnectionFailedException(Throwable cause) {
        super(cause);
    }

    public ExternalServiceConnectionFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
