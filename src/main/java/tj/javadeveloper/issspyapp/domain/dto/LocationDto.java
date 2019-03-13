package tj.javadeveloper.issspyapp.domain.dto;

import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LocationDto extends BaseDto implements Comparable<LocationDto>, Serializable {

    private Long time;
    private Double latitude;
    private Double longitude;

    @Builder
    public LocationDto(Long id, Long time, Double latitude, Double longitude) {
        super(id);
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public int compareTo(LocationDto o) {
        return (int) (this.time - o.getTime());
    }

}
