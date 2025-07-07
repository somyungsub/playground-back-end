package prototype.hexa.sample.adapter.out.persistence.sample.noassociation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import prototype.hexa.sample.adapter.out.persistence.sample.noassociation.SampleInputNoAssociationJpaEntity;
import prototype.hexa.sample.adapter.out.persistence.sample.noassociation.SampleNoAssociationJpaEntity;

@Getter
@AllArgsConstructor
class JoinSample {
  private final SampleNoAssociationJpaEntity sampleNoAssociationJpa;
  private final SampleInputNoAssociationJpaEntity sampleInputNoAssociationJpaEntity;
}
