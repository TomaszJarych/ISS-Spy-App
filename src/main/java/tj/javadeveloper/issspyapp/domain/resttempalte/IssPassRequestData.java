package tj.javadeveloper.issspyapp.domain.resttempalte;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssPassRequestData implements Serializable {
    @JsonProperty("altitude")
    private Long altitude;
    @JsonProperty("datetime")
    private Long requestTime;
    @JsonProperty("latitude")
    private Long latitude;
    @JsonProperty("longitude")
    private Long longitude;
    @JsonProperty("passes")
    private Integer passes;

    public Long getAltitude() {
        return altitude;
    }

    public void setAltitude(Long altitude) {
        this.altitude = altitude;
    }

    public Long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Integer getPasses() {
        return passes;
    }

    public void setPasses(Integer passes) {
        this.passes = passes;
    }

    @Override
    public String toString() {
        return "IssPassRequestData{" +
                "altitude=" + altitude +
                ", requestTime=" + requestTime +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", passes=" + passes +
                '}';
    }
}
