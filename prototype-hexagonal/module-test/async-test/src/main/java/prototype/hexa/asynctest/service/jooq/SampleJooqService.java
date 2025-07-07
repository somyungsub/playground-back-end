package prototype.hexa.asynctest.service.jooq;

import prototype.hexa.asynctest.domain.Sample;

public interface SampleJooqService {
  Sample findById(long id);

  Sample joinSample(long id);
  Sample findCtas(long id);

  void createTable(long id);
}
