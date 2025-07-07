package prototype.hexa.sample.adapter.out.persistence.sample.association;

import org.springframework.data.jpa.repository.JpaRepository;

interface SampleRepository extends JpaRepository<SampleJpaEntity, Long> {
}
