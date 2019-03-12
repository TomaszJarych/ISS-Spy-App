package tj.javadeveloper.issspyapp.domain.resttempalte;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"iss_position", "message", "timestamp"})
public class ISSLocation implements Serializable {

    @JsonProperty("message")
    private String message;
    @JsonProperty("timestamp")
    private Long timestamp;
    @JsonProperty("iss_position")
    private GeoCoordinates issPosition;

    public ISSLocation() {
    }

    public String getMessage() {
        return this.message;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public GeoCoordinates getIssPosition() {
        return this.issPosition;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setIssPosition(GeoCoordinates issPosition) {
        this.issPosition = issPosition;
    }
}
