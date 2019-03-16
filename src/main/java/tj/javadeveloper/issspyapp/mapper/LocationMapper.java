package tj.javadeveloper.issspyapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tj.javadeveloper.issspyapp.domain.dto.LocationDto;
import tj.javadeveloper.issspyapp.domain.entity.LocationEntity;
import tj.javadeveloper.issspyapp.domain.resttempalte.ISSLocation;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    @Mapping(source = "timestamp", target = "time")
    @Mapping(source = "issPosition.latitude", target = "latitude")
    @Mapping(source = "issPosition.longitude", target = "longitude")
    LocationDto toLocationDto(ISSLocation issLocation);

    LocationEntity toLocationEntity(LocationDto dto);

    LocationDto fromLocationEntityToLocationDto(LocationEntity entity);

}
