package tj.javadeveloper.issspyapp.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class LocationDto extends BaseDto implements Comparable<LocationDto> {

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
