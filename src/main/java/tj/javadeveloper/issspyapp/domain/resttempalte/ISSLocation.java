package tj.javadeveloper.issspyapp.domain.resttempalte;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"iss_position", "message", "timestamp"})
public class ISSLocation implements Serializable {

    @JsonProperty("message")
    private String message;
    @JsonProperty("timestamp")
    private Long timestamp;
    @JsonProperty("iss_position")
    private GeoCoordinates issPosition;

}
