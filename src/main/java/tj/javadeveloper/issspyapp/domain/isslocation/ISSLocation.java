package tj.javadeveloper.issspyapp.domain.isslocation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"iss_position", "message", "timestamp"})
@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class ISSLocation implements Serializable {

    @JsonProperty("message")
    private String message;
    @JsonProperty("timestamp")
    private Long timestamp;
    @JsonProperty("iss_position")
    private GeoCoordinates issPosition;

}
