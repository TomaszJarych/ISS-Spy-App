package tj.javadeveloper.issspyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tj.javadeveloper.issspyapp.domain.entity.LocationEntity;

@Repository
public interface LocationEntityRepository extends JpaRepository<LocationEntity, Long> {
}
