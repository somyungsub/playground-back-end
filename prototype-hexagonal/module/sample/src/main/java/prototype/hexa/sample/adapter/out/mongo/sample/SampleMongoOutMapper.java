package prototype.hexa.sample.adapter.out.mongo.sample;


import prototype.hexa.common.annotation.PersistenceAdapter;
import prototype.hexa.sample.domain.sample.Sample;
import prototype.hexa.sample.domain.sample.SampleInput;
import prototype.hexa.sample.domain.sample.constant.SampleCode;

import java.util.Collections;
import java.util.List;

@PersistenceAdapter
class SampleMongoOutMapper {
//
    Sample toDomain(SampleDocument sampleDocument) {
        List<SampleInput> inputs = sampleDocument.getInputs().stream()
                .map(inputDocument -> SampleInput.withId(inputDocument.getInputId(), inputDocument.getName(), inputDocument.getValue()))
                .toList();

        return Sample.withId(
                sampleDocument.getSampleId(),
                sampleDocument.getName(),
                SampleCode.valueOf(sampleDocument.getCode()),
                inputs
        );
    }

    SampleDocument toEntity(Sample sample) {
        SampleDocument sampleJpaEntity = SampleDocument.of(
                sample.getId() == null ? (int) Math.ceil(Math.random() * 10000) : sample.getId(),
                sample.getName(),
                sample.getCode().name(),
                Collections.emptyList()
        );
        return sampleJpaEntity;
    }
}
