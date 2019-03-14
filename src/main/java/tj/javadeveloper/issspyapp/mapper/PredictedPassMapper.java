package tj.javadeveloper.issspyapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tj.javadeveloper.issspyapp.domain.dto.PredictedPassDto;
import tj.javadeveloper.issspyapp.domain.resttempalte.IssPredictedPass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface PredictedPassMapper {

    PredictedPassMapper INSTANCE = Mappers.getMapper(PredictedPassMapper.class);

    @Mapping(source = "requestData.passes", target = "passesNumber")
    @Mapping(source = "requestData.latitude", target = "latitude")
    @Mapping(source = "requestData.longitude", target = "longitude")
    @Mapping(source = "passesList", target = "passesData")
    PredictedPassDto toPredictedPassDto(IssPredictedPass input);

    default LocalDateTime convertTimeStampToLocalDateTime(Long risetime) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(risetime), ZoneId.systemDefault());
    }
}
