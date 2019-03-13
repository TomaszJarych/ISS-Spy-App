package tj.javadeveloper.issspyapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TotalDistanceResult {
    @JsonProperty("TotalDistance")
    long distance;

}
