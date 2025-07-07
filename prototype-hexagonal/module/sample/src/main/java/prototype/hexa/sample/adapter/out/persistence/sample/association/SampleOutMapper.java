package prototype.hexa.sample.adapter.out.persistence.sample.association;

import org.springframework.stereotype.Component;
import prototype.hexa.sample.domain.sample.Sample;
import prototype.hexa.sample.domain.sample.SampleInput;
import prototype.hexa.sample.domain.sample.constant.SampleCode;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
class SampleOutMapper {

    Sample toDomain(SampleJpaEntity sampleJpaEntity) {
        List<SampleInput> sampleInputList = sampleJpaEntity.getInputEntities().stream()
                .map(entity -> SampleInput.withId(entity.getId(), entity.getName(), entity.getValue()))
                .collect(toList());

        return Sample.withId(
                sampleJpaEntity.getId(),
                sampleJpaEntity.getName(),
                SampleCode.valueOf(sampleJpaEntity.getCode()),
                sampleInputList
        );
    }

    SampleJpaEntity toEntity(Sample sample) {
        SampleJpaEntity sampleJpaEntity = SampleJpaEntity.of(
                sample.getId(),
                sample.getName(),
                sample.getCode().name(),
                sample.getInputs()
        );

        return sampleJpaEntity;
    }
}
