package prototype.hexa.sample.adapter.out.persistence.sample.noassociation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface SampleNoAssociationRepository extends JpaRepository<SampleNoAssociationJpaEntity, Long> {
  @Query("SELECT new prototype.hexa.sample.adapter.out.persistence.sample.noassociation.JoinSample(s, si) FROM SampleNoAssociationJpaEntity s JOIN SampleInputNoAssociationJpaEntity si ON s.id = si.sampleId and s.id=:sampleId")
  List<JoinSample> joinSample(Long sampleId);
}
