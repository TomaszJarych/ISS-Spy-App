package tj.javadeveloper.issspyapp.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tj.javadeveloper.issspyapp.domain.dto.PredictedPassDataDto;
import tj.javadeveloper.issspyapp.domain.dto.PredictedPassDto;
import tj.javadeveloper.issspyapp.domain.resttempalte.IssPassData;
import tj.javadeveloper.issspyapp.domain.resttempalte.IssPassRequestData;
import tj.javadeveloper.issspyapp.domain.resttempalte.IssPredictedPass;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PredictedPassMapperTest {

    private PredictedPassMapper mapper;
    private Long altitude;
    private Long requestTime;
    private Double latitude;
    private Double longitude;
    private Integer passes;
    private Integer duration1;
    private Long risetime1;
    private Integer duration2;
    private Long risetime2;
    private Integer duration3;
    private Long risetime3;
    private Integer duration4;
    private Long risetime4;
    private Integer duration5;
    private Long risetime5;

    @BeforeEach
    void setUp() {
        mapper = PredictedPassMapper.INSTANCE;
        altitude = 100L;
        latitude = 51.1607;
        longitude = 17.1338;
        passes = 5;
        duration1 = 461;
        risetime1 = 1552595791L;
        duration2 = 623;
        risetime2 = 1552601456L;
        duration3 = 645;
        risetime3 = 1552607228L;
        duration4 = 645;
        risetime4 = 1552613025L;
        duration5 = 621;
        risetime5 = 1552618819L;
        requestTime = 1552581150L;
    }

    @Test
    void PredictedPassDtoMapperMethodTest() {
        //given
        IssPassData passData1 = IssPassData.builder().duration(duration1).risetime(risetime1).build();
        IssPassData passData2 = IssPassData.builder().duration(duration2).risetime(risetime2).build();
        IssPassData passData3 = IssPassData.builder().duration(duration3).risetime(risetime3).build();
        IssPassData passData4 = IssPassData.builder().duration(duration4).risetime(risetime4).build();
        IssPassData passData5 = IssPassData.builder().duration(duration5).risetime(risetime5).build();
        List<IssPassData> issPassDataList = Arrays.asList(passData1, passData2, passData3, passData4, passData5);

        IssPassRequestData issPassRequestData = IssPassRequestData.builder()
                .altitude(altitude)
                .requestTime(requestTime)
                .latitude(latitude)
                .longitude(longitude)
                .passes(passes)
                .build();

        IssPredictedPass issPredictedPass = IssPredictedPass.builder()
                .message("success")
                .requestData(issPassRequestData)
                .passesList(issPassDataList)
                .build();

        PredictedPassDataDto passDataDto1 = PredictedPassDataDto.builder().duration(duration1)
                .risetime(convertTimeStampToLocalDateTime(risetime1)).build();
        PredictedPassDataDto passDataDto2 = PredictedPassDataDto.builder().duration(duration2)
                .risetime(convertTimeStampToLocalDateTime(risetime2)).build();
        PredictedPassDataDto passDataDto3 = PredictedPassDataDto.builder().duration(duration3)
                .risetime(convertTimeStampToLocalDateTime(risetime3)).build();
        PredictedPassDataDto passDataDto4 = PredictedPassDataDto.builder().duration(duration4)
                .risetime(convertTimeStampToLocalDateTime(risetime4)).build();
        PredictedPassDataDto passDataDto5 = PredictedPassDataDto.builder().duration(duration5)
                .risetime(convertTimeStampToLocalDateTime(risetime5)).build();

        List<PredictedPassDataDto> predictedPassDataDtoList = Arrays.asList(passDataDto1, passDataDto2, passDataDto3,
                passDataDto4, passDataDto5);
        PredictedPassDto expected = PredictedPassDto.builder()
                .latitude(latitude)
                .longitude(longitude)
                .passesNumber(passes)
                .passesData(predictedPassDataDtoList)
                .build();

        //when

        PredictedPassDto actual = mapper.toPredictedPassDto(issPredictedPass);

        //then
        assertNotNull(actual);
        assertAll(
                () -> assertEquals(expected.getLatitude(), actual.getLatitude()),
                () -> assertEquals(expected.getLongitude(), actual.getLongitude()),
                () -> assertEquals(expected.getPassesNumber(), actual.getPassesNumber()),
                () -> assertNotNull(actual.getPassesData()),
                () -> assertEquals(expected.getPassesData().size(), actual.getPassesData().size())
        );
        for (int i = 0; i < expected.getPassesData().size(); i++) {
            assertEquals(expected.getPassesData().get(i).getDuration(), actual.getPassesData().get(i).getDuration());
            assertEquals(expected.getPassesData().get(i).getRisetime(), actual.getPassesData().get(i).getRisetime());
        }


    }

    private LocalDateTime convertTimeStampToLocalDateTime(Long risetime) {
        return mapper.convertTimeStampToLocalDateTime(risetime);
    }
}