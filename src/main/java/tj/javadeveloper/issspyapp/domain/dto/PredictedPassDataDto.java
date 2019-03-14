package tj.javadeveloper.issspyapp.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor

public class PredictedPassDataDto {
    private Integer duration;
    private LocalDateTime risetime;

    @Builder
    private PredictedPassDataDto(Integer duration, LocalDateTime risetime) {
        this.duration = duration;
        this.risetime = risetime;
    }
}
