package prototype.hexa.membership.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberShipRepository extends JpaRepository<MemberJpaEntity, Long> {
  Optional<MemberJpaEntity> findByName(String name);
}
