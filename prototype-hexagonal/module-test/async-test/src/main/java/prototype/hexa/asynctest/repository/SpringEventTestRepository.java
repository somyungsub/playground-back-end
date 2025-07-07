package prototype.hexa.asynctest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prototype.hexa.asynctest.repository.entity.SpringEventTestEntity;

public interface SpringEventTestRepository extends JpaRepository<SpringEventTestEntity, Long> {
}
