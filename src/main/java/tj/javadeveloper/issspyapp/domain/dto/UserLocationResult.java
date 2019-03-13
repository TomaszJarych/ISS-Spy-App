package tj.javadeveloper.issspyapp.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class UserLocationResult extends BaseDto implements Serializable {

    private String locationName;
    private Double distance;
    private long time;
    private String country;
    private String latitude;
    private String longitude;


    @Builder
    private UserLocationResult(Long id, String locationName, Double distance, long time,
                               String country, String latitude, String longitude) {
        super(id);
        this.locationName = locationName;
        this.distance = distance;
        this.time = time;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
