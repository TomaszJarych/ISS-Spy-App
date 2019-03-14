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
public class IssPassData implements Serializable, Comparable<IssPassData> {

    @JsonProperty("duration")
    private Integer duration;
    @JsonProperty("risetime")
    private Long risetime;

    @Builder
    private IssPassData(Integer duration, Long risetime) {
        this.duration = duration;
        this.risetime = risetime;
    }

    @Override
    public int compareTo(IssPassData o) {
        return Math.toIntExact(this.risetime - o.risetime);
    }
}
