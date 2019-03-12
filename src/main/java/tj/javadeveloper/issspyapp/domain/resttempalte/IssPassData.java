package tj.javadeveloper.issspyapp.domain.resttempalte;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssPassData implements Serializable, Comparable<IssPassData> {

    @JsonProperty("duration")
    private Integer duration;

    @JsonProperty("risetime")
    private Long risetime;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getRisetime() {
        return risetime;
    }

    public void setRisetime(Long risetime) {
        this.risetime = risetime;
    }

    @Override
    public String toString() {
        return "IssPassData{" +
                "duration=" + duration +
                ", risetime=" + risetime +
                '}';
    }

    @Override
    public int compareTo(IssPassData o) {
        return Math.toIntExact(this.risetime - o.risetime);
    }
}
