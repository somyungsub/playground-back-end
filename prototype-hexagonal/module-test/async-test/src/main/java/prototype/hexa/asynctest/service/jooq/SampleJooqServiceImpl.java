package prototype.hexa.asynctest.service.jooq;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import prototype.hexa.asynctest.domain.Sample;
import prototype.hexa.asynctest.repository.SampleRepository;
import prototype.hexa.common.annotation.UseCase;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
class SampleJooqServiceImpl implements SampleJooqService {
  private final SampleRepository sampleRepository;
  @Override
  public Sample findById(long id) {
    return Optional
      .ofNullable(sampleRepository.findById(id))
      .orElseThrow(() -> new RuntimeException("실패"));
  }

  @Override
  public Sample joinSample(long id) {
    return sampleRepository.joinSample(id);
  }

  @Override
  public Sample findCtas(long id) {
    return sampleRepository.findCtas(id);
  }

  @Override
  @Transactional(transactionManager = "jdbcTransactionManager")
  public void createTable(long id) {
    sampleRepository.createTable(id);
  }

}
