package tj.javadeveloper.issspyapp.commons.exceptions;

public class InternalServerCustomException extends RuntimeException {
    public InternalServerCustomException() {
        super();
    }

    public InternalServerCustomException(String message) {
        super(message);
    }

    public InternalServerCustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalServerCustomException(Throwable cause) {
        super(cause);
    }

    protected InternalServerCustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
