package tj.javadeveloper.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = GeoCoordinates.GeoCoordinatesBuilder.class)
@JsonPOJOBuilder(withPrefix = "")
public class GeoCoordinates {
    private Long longitude;
    private Long latitude;

    @Builder(builderClassName = "GeoCoordinatesBuilder", toBuilder = true)
    private GeoCoordinates(Long longitude, Long latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
