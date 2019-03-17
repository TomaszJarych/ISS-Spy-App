package tj.javadeveloper.issspyapp.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor

public class PredictedPassDataDto implements Serializable {
    private Integer duration;
    private LocalDateTime risetime;

    @Builder
    private PredictedPassDataDto(Integer duration, LocalDateTime risetime) {
        this.duration = duration;
        this.risetime = risetime;
    }
}
