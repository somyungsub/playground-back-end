package prototype.hexa.sample.application.service.sample;

import org.springframework.transaction.annotation.Transactional;
import prototype.hexa.common.annotation.UseCase;
import prototype.hexa.common.exception.GlobalException;
import prototype.hexa.sample.application.port.in.sample.SampleUseCase;
import prototype.hexa.sample.application.port.in.sample.command.SampleSaveCommand;
import prototype.hexa.sample.application.port.in.sample.command.SampleUpdateCommand;
import prototype.hexa.sample.application.port.out.sample.SampleOutPort;
import prototype.hexa.sample.application.port.out.sample.SampleQueryDslOutPort;
import prototype.hexa.sample.domain.sample.Sample;

import java.util.Optional;

@UseCase
class SampleUseCaseService implements SampleUseCase {
  private final SampleOutPort sampleOutPort;
  private final SampleQueryDslOutPort sampleQueryDslOutPort;

  public SampleUseCaseService(
          SampleOutPort sampleOutPort,
          SampleQueryDslOutPort sampleQueryDslOutPort)
//    @Qualifier("sampleOutPortNoAssociationAdapter") SampleOutPort sampleOutPort,
//    @Qualifier("sampleOutPortNoAssociationAdapter") SampleQueryDslOutPort sampleQueryDslOutPort)
  {
    this.sampleOutPort = sampleOutPort;
    this.sampleQueryDslOutPort = sampleQueryDslOutPort;
  }

  @Override
  public Sample findById(Long sampleId) {
    Sample sample = Optional
            .ofNullable(sampleOutPort.findById(sampleId))
            .orElseThrow(() -> new GlobalException("WNE-HRS-SAMPLE-0002", String.valueOf(sampleId)));
    sample.validation();
    return sample;
  }

  @Override
  public Sample findByIdDsl(Long sampleId) {
    Sample sample = Optional
            .ofNullable(sampleQueryDslOutPort.findByIdDsl(sampleId))
            .orElseThrow(() -> new GlobalException("WNE-HRS-SAMPLE-0001", "hello~world"));
    sample.validation();
    return sample;
  }

  @Override
  @Transactional
  public Sample saveSample(SampleSaveCommand sampleSaveCommand) {
    Sample sample = Sample.withoutId(sampleSaveCommand);
    return Optional
            .ofNullable(sampleOutPort.save(sample))
            .orElseThrow(() -> new GlobalException("WNE-HRS-SAMPLE-0001", sampleSaveCommand.getName()));
  }

  @Override
  @Transactional
  public void deleteSample(Long sampleId) {
    sampleOutPort.delete(sampleId);
//    if (true) {
//      throw new GlobalException("WNE-HRS-SAMPLE-0004", sampleId);
//    }
  }

  @Override
  @Transactional
  public Sample updateSample(Long sampleId, SampleUpdateCommand sampleUpdateCommand) {
    Sample sample = Optional
            .ofNullable(sampleOutPort.findById(sampleId))
            .orElseThrow(() -> new GlobalException("WNE-HRS-SAMPLE-0001", sampleId));
    Sample updateSample = sample.withUpdate(sampleUpdateCommand);
    return sampleOutPort.update(updateSample);
  }
}
