package tj.javadeveloper.issspyapp.domain.resttempalte;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IPLocationData implements Serializable {

    @JsonProperty("city")
    private String cityName;
    @JsonProperty("country")
    private String country;
    @JsonProperty("countryCode")
    private String countryCode;
    @JsonProperty("lat")
    private String latitude;
    @JsonProperty("lon")
    private String longitude;
    @JsonProperty("status")
    private String status;

}
