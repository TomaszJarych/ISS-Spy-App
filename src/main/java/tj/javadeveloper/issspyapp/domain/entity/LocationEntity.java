package tj.javadeveloper.issspyapp.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
public class LocationEntity extends BaseEntity {

    @Column(name = "request_time")
    private Long time;
    private Double latitude;
    private Double longitude;

    @Builder
    private LocationEntity(Long id, Long time, Double latitude, Double longitude) {
        super(id);
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
