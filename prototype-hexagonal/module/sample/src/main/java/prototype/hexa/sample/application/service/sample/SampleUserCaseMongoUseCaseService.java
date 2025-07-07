package prototype.hexa.sample.application.service.sample;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import prototype.hexa.common.annotation.UseCase;
import prototype.hexa.common.exception.GlobalException;
import prototype.hexa.sample.application.port.in.sample.SampleMongoUseCase;
import prototype.hexa.sample.application.port.in.sample.command.SampleSaveCommand;
import prototype.hexa.sample.application.port.in.sample.command.SampleUpdateCommand;
import prototype.hexa.sample.application.port.out.sample.SampleMongoOutPort;
import prototype.hexa.sample.domain.sample.Sample;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
class SampleUserCaseMongoUseCaseService implements SampleMongoUseCase {
  private final SampleMongoOutPort sampleMongoOutPort;

  @Override
  public Sample findById(Long sampleId) {
    Sample sample = Optional
            .ofNullable(sampleMongoOutPort.findById(sampleId))
            .orElseThrow(() -> new GlobalException("WNE-HRS-SAMPLE-0001", sampleId));
    sample.validation();
    return sample;
  }

  @Override
  public Sample findByIdDsl(Long sampleId) {
    // TODO
    return null;
  }

  @Override
  @Transactional(transactionManager = "mongoTransactionManager")
  public Sample saveSample(SampleSaveCommand sampleSaveCommand) {
    Sample sample = Sample.withoutId(sampleSaveCommand);
    return Optional
            .ofNullable(sampleMongoOutPort.save(sample))
            .orElseThrow(() -> new GlobalException("WNE-HRS-SAMPLE-0001", sample.getName()));
  }

  @Override
  public void deleteSample(Long sampleId) {
    sampleMongoOutPort.delete(sampleId);
  }

  @Override
  public Sample updateSample(Long sampleId, SampleUpdateCommand sampleUpdateCommand) {
    Sample sample = Optional
            .ofNullable(sampleMongoOutPort.findById(sampleId))
            .orElseThrow(() -> new GlobalException("WNE-HRS-SAMPLE-0001", sampleId));
    Sample withSample = sample.withUpdate(sampleUpdateCommand);
    return sampleMongoOutPort.update(withSample);
  }
}
