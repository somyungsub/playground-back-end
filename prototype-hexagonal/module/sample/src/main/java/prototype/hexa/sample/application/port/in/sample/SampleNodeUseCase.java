package prototype.hexa.sample.application.port.in.sample;

import prototype.hexa.sample.domain.sample.Sample;
import prototype.hexa.sample.domain.sample.SampleNode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SampleNodeUseCase {
  Mono<Sample> findSample(Long id);

  SampleNode syncFindSample(Long id);
  Mono<SampleNode> asyncFindSample(Long id);

  Flux<SampleNode> asyncFindSamples(Long id);
}
