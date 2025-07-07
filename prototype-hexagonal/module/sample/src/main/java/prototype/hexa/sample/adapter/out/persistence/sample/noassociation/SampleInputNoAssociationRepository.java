package prototype.hexa.sample.adapter.out.persistence.sample.noassociation;

import org.springframework.data.jpa.repository.JpaRepository;

interface SampleInputNoAssociationRepository extends JpaRepository<SampleInputNoAssociationJpaEntity, Long> {
  void deleteBySampleId(Long sampleId);
}
