package prototype.hexa.sample.adapter.out.mongo.sample;

import lombok.RequiredArgsConstructor;
import prototype.hexa.common.annotation.PersistenceAdapter;
import prototype.hexa.sample.application.port.out.sample.SampleMongoOutPort;
import prototype.hexa.sample.domain.sample.Sample;

import java.util.Optional;


@PersistenceAdapter
@RequiredArgsConstructor
class SampleMongoOutPortAdapter implements SampleMongoOutPort {
    private final SampleMongoRepository sampleMongoRepository;
    private final SampleMongoOutMapper sampleMongoOutMapper;

    @Override
    public Sample save(Sample sample) {
        SampleDocument entity = sampleMongoOutMapper.toEntity(sample);
        SampleDocument save = sampleMongoRepository.save(entity);
        return sampleMongoOutMapper.toDomain(save);
    }

    @Override
    public Sample findById(Long sampleId) {
        SampleDocument document = sampleMongoRepository.findBySampleId(sampleId);
        return sampleMongoOutMapper.toDomain(document);
    }

    @Override
    public void delete(Long sampleId) {
        //TODO
    }

    @Override
    public Sample update(Sample sample) {
        //TODO
        SampleDocument entity = sampleMongoOutMapper.toEntity(sample);
        SampleDocument save = sampleMongoRepository.save(entity);
        return sampleMongoOutMapper.toDomain(save);
    }

}
