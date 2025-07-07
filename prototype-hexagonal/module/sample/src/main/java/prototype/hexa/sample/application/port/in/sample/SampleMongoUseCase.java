package prototype.hexa.sample.application.port.in.sample;


import prototype.hexa.sample.application.port.in.sample.command.SampleSaveCommand;
import prototype.hexa.sample.application.port.in.sample.command.SampleUpdateCommand;
import prototype.hexa.sample.domain.sample.Sample;

public interface SampleMongoUseCase {
  Sample findById(Long sampleId);

  Sample findByIdDsl(Long sampleId);

  Sample saveSample(SampleSaveCommand sampleSaveCommand);

  void deleteSample(Long sampleId);

  Sample updateSample(Long sampleId, SampleUpdateCommand sampleUpdateCommand);
}
