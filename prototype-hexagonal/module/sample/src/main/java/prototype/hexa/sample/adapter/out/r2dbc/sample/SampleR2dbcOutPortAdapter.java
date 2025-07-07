package prototype.hexa.sample.adapter.out.r2dbc.sample;

import lombok.RequiredArgsConstructor;
import prototype.hexa.common.annotation.PersistenceAdapter;
import prototype.hexa.sample.application.port.out.sample.SampleR2dbcOutPort;
import prototype.hexa.sample.domain.sample.Sample;
import reactor.core.publisher.Mono;

import java.util.List;


@PersistenceAdapter
@RequiredArgsConstructor
class SampleR2dbcOutPortAdapter implements SampleR2dbcOutPort {
    private final SampleR2dbcRepository sampleR2dbcRepository;
    private final SampleInputR2dbcRepository sampleInputR2dbcRepository;
    private final SampleR2dbcOutMapper sampleR2dbcOutMapper;

    @Override
    public Mono<Sample> save(Sample sample) {
        SampleR2dbc entity = sampleR2dbcOutMapper.toEntity(sample);
        return sampleR2dbcRepository.save(entity)
          .flatMap(savedSample -> {
              List<SampleInputR2dbc> sampleInputs = sampleR2dbcOutMapper.toEntityInputs(sample.getInputs(), savedSample.getId());
              return sampleInputR2dbcRepository.saveAll(sampleInputs)
                .collectList()
                .flatMap(savedInputs -> {
                    // 여기에 추가적인 로직이 있을 수 있습니다.
                    return Mono.just(sampleR2dbcOutMapper.toDomain(savedSample, savedInputs));
                });
          });
    }

    @Override
    public Mono<Sample> findById(Long sampleId) {
        return sampleR2dbcRepository.findById(sampleId)
                .flatMap(sampleR2dbc -> sampleInputR2dbcRepository.findBySampleId(sampleId)
                        .collectList()
                        .flatMap(inputs -> Mono.just(sampleR2dbcOutMapper.toDomain(sampleR2dbc, inputs))));
    }

    @Override
    public Mono<Sample> findByName(String name) {
        return sampleR2dbcRepository.findByName(name)
                .flatMap(sampleR2dbc -> sampleInputR2dbcRepository.findBySampleId(sampleR2dbc.getId())
                        .collectList()
                        .flatMap(inputs -> Mono.just(sampleR2dbcOutMapper.toDomain(sampleR2dbc, inputs))));
    }

}
