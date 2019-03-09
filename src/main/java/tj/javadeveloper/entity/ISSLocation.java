package tj.javadeveloper.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ISSLocation {

    private String message;
    private Long timestamp;
    private GeoCoordinates issPosition;

    @Builder
    private ISSLocation(String message, Long timestamp, GeoCoordinates issPosition) {
        this.message = message;
        this.timestamp = timestamp;
        this.issPosition = issPosition;
    }
}
