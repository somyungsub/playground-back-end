package prototype.hexa.sample.application.port.out.sample;


import prototype.hexa.sample.domain.sample.Sample;
import reactor.core.publisher.Mono;

public interface SampleR2dbcOutPort {
  Mono<Sample> save(Sample sample);

  Mono<Sample> findById(Long sampleId);

  Mono<Sample> findByName(String name);
//    void deleteSample(Long sampleId);
//    Sample updateSample(Long sampleId, Sample sample);
}
