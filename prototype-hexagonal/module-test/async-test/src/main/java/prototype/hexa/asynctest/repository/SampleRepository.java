package prototype.hexa.asynctest.repository;

import prototype.hexa.asynctest.domain.Sample;

public interface SampleRepository {
  Sample findById(long id);

  Sample joinSample(long sampleId);

  void createTable(long id);

  Sample findCtas(long id);

}
