package tj.javadeveloper.issspyapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TotalDistanceResult implements Serializable {
    @JsonProperty("TotalDistance")
    long distance;

}
