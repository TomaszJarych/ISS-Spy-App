package tj.javadeveloper.issspyapp.repository;

import org.springframework.stereotype.Repository;
import tj.javadeveloper.issspyapp.domain.dto.LocationDto;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class LocationRepository {
    private AtomicLong atomicLong;
    private Set<LocationDto> locationRepository;

    @PostConstruct
    public void setUp() {
        locationRepository = new HashSet<>();
        atomicLong = new AtomicLong(1);
    }

    public void saveLocation(LocationDto dto) {
        locationRepository.add(addIdToLocationDto(dto));
    }

    private LocationDto addIdToLocationDto(LocationDto dto) {
        LocationDto locationDto = LocationDto.builder()
                .id(atomicLong.getAndIncrement())
                .time(dto.getTime())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
        return locationDto;
    }

    public Set<LocationDto> findAll() {
        return this.locationRepository;
    }
}
