package tj.javadeveloper.issspyapp.domain.resttempalte;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"latitude", "longitude"})
public class GeoCoordinates implements Serializable {
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("longitude")
    private String longitude;

    public GeoCoordinates() {
    }

    public String getLatitude() {
        return this.latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
