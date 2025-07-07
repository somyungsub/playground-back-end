package prototype.hexa.sample.adapter.out.persistence.sample.noassociation;

import org.springframework.stereotype.Component;
import prototype.hexa.sample.domain.sample.Sample;
import prototype.hexa.sample.domain.sample.SampleInput;
import prototype.hexa.sample.domain.sample.constant.SampleCode;

import java.util.List;

@Component
class SampleNoOutMapper {
    SampleNoAssociationJpaEntity toEntity(Sample sample) {
      return SampleNoAssociationJpaEntity.of(
          sample.getId(),
          sample.getName(),
          sample.getCode().name()
        );
    }

    Sample toDomain(SampleNoAssociationJpaEntity sampleJpaEntity, List<SampleInputNoAssociationJpaEntity> inputs) {
        List<SampleInput> sampleInputList = inputs.stream()
          .map(entity -> SampleInput.withId(entity.getId(), entity.getName(), entity.getValue()))
          .toList();

        return Sample.withId(
          sampleJpaEntity.getId(),
          sampleJpaEntity.getName(),
          SampleCode.valueOf(sampleJpaEntity.getCode()),
          sampleInputList
        );
    }

    SampleInputNoAssociationJpaEntity toInputEntity(SampleInput sampleInput, Long sampleId) {
        return SampleInputNoAssociationJpaEntity.of(
          sampleInput.getId(),
          sampleInput.getName(),
          sampleInput.getValue(),
          sampleId
        );
    }
}
