package prototype.hexa.sample.application.service.sample;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import prototype.hexa.common.annotation.UseCase;
import prototype.hexa.common.exception.GlobalException;
import prototype.hexa.sample.application.port.in.sample.SampleR2dbcUseCase;
import prototype.hexa.sample.application.port.in.sample.command.SampleSaveCommand;
import prototype.hexa.sample.application.port.out.sample.SampleR2dbcOutPort;
import prototype.hexa.sample.domain.sample.Sample;
import reactor.core.publisher.Mono;

@UseCase
@RequiredArgsConstructor
class SampleUseCaseR2dbcService implements SampleR2dbcUseCase {
  private final SampleR2dbcOutPort sampleR2dbcOutPort;

  @Override
  @Transactional
  public Mono<Sample> saveSample(SampleSaveCommand sampleSaveCommand) {
    Sample sample = Sample.withoutId(sampleSaveCommand);
    return sampleR2dbcOutPort.save(sample);
  }

  @Override
  public Mono<Sample> findById(Long sampleId) {
    return sampleR2dbcOutPort
      .findById(sampleId)
      .switchIfEmpty(Mono.error(() -> new GlobalException("WNE-HRS-SAMPLE-0003", sampleId)))
      ;
  }

  @Override
  public Mono<Sample> findByName(String name) {
    return sampleR2dbcOutPort.findByName(name)
      .switchIfEmpty(Mono.error(() -> new GlobalException("WNE-HRS-SAMPLE-0003", name)));
  }
}
