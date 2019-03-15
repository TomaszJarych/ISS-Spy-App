package tj.javadeveloper.issspyapp.commons.exceptions;

public class InvalidLocationDataException extends RuntimeException {
    public InvalidLocationDataException() {
        super();
    }

    public InvalidLocationDataException(String message) {
        super(message);
    }

    public InvalidLocationDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLocationDataException(Throwable cause) {
        super(cause);
    }

    protected InvalidLocationDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
