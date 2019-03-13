package tj.javadeveloper.issspyapp.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultWrapper {
    private String message;
    private Long timestamp;
    private Object result;


    public static ResultWrapper ok(String message, Object result) {
        return new ResultWrapper(message, Instant.now().getEpochSecond(), result);
    }

    public static ResultWrapper ok(Object result) {
        return new ResultWrapper("success", Instant.now().getEpochSecond(), result);
    }

    public static ResultWrapper ok() {
        return new ResultWrapper("success", Instant.now().getEpochSecond(), null);
    }

    public static ResultWrapper error(String message, Object result) {
        return new ResultWrapper(message, Instant.now().getEpochSecond(), result);
    }

    public static ResultWrapper error(Object result) {
        return new ResultWrapper("failed", Instant.now().getEpochSecond(), result);
    }

    public static ResultWrapper error(String message) {
        return new ResultWrapper(message, Instant.now().getEpochSecond(), null);
    }

    public static ResultWrapper error() {
        return new ResultWrapper("failed", Instant.now().getEpochSecond(), null);
    }

}
