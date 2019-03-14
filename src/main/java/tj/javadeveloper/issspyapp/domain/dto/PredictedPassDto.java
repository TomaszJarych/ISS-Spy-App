package tj.javadeveloper.issspyapp.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PredictedPassDto {
    private Integer passesNumber;
    private Double latitude;
    private Double longitude;
    List<PredictedPassDataDto> passesData = new ArrayList<>();

    @Builder
    private PredictedPassDto(Integer passesNumber, Double latitude, Double longitude,
                             List<PredictedPassDataDto> passesData) {
        this.passesNumber = passesNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.passesData = passesData;
    }
}
