package prototype.hexa.sample.application.port.in.sample;


import prototype.hexa.sample.application.port.in.sample.command.SampleSaveCommand;
import prototype.hexa.sample.domain.sample.Sample;
import reactor.core.publisher.Mono;

public interface SampleR2dbcUseCase {

  Mono<Sample> saveSample(SampleSaveCommand sampleSaveCommand);
  Mono<Sample> findById(Long sampleId);
  Mono<Sample> findByName(String name);
}
