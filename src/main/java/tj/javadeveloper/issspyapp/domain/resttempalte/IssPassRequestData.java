package tj.javadeveloper.issspyapp.domain.resttempalte;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssPassRequestData implements Serializable {
    @JsonProperty("altitude")
    private Long altitude;
    @JsonProperty("datetime")
    private Long requestTime;
    @JsonProperty("latitude")
    private Double latitude;
    @JsonProperty("longitude")
    private Double longitude;
    @JsonProperty("passes")
    private Integer passes;

    @Builder
    private IssPassRequestData(Long altitude, Long requestTime, Double latitude, Double longitude, Integer passes) {
        this.altitude = altitude;
        this.requestTime = requestTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.passes = passes;
    }
}
