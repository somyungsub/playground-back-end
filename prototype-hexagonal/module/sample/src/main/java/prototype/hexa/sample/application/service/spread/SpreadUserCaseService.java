package prototype.hexa.sample.application.service.spread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import prototype.hexa.common.annotation.UseCase;
import prototype.hexa.common.exception.GlobalException;
import prototype.hexa.sample.application.port.in.spread.SpreadUseCase;
import prototype.hexa.sample.application.port.in.spread.SpreadTypeOfUseCase;
import prototype.hexa.sample.application.port.in.spread.command.SpreadSaveCommand;
import prototype.hexa.sample.application.port.out.spread.SpreadEventOutPort;
import prototype.hexa.sample.application.port.out.spread.SpreadOutPort;
import prototype.hexa.sample.application.port.out.spread.SpreadTypeOfOutPort;
import prototype.hexa.sample.config.properties.SampleProperties;
import prototype.hexa.sample.domain.spread.Spread;
import reactor.core.publisher.Mono;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
@Slf4j
class SpreadUserCaseService implements SpreadUseCase, SpreadTypeOfUseCase {
  private final SpreadOutPort spreadOutPort;
  private final SpreadEventOutPort spreadEventOutPort;
  private final SpreadTypeOfOutPort spreadTypeOfOutPort;
  private final SampleProperties sampleProperties;

  @Override
  @Transactional(transactionManager = "mongoTransactionManager")
  public Spread saveSpread(SpreadSaveCommand spreadSaveCommand) {
    Spread spread = Spread.withoutId(spreadSaveCommand);
    Spread result = Optional
            .ofNullable(spreadOutPort.save(spread))
            .orElseThrow(() -> new GlobalException("WNE-HRS-SPREAD-0001", spread.getName()));
//    if (true) {
//      throw new GlobalException("WNE-HRS-SAMPLE-0001", spread.getName());
//    }
    spreadEventOutPort.send(sampleProperties.topicName(), result);
    return result;
  }

  @Override
  public Spread findByName(String name) {
    return Optional
            .ofNullable(spreadOutPort.findByName(name))
            .orElseThrow(() -> new GlobalException("WNE-HRS-SPREAD-0001", name));
  }

  @Override
  public Mono<Spread> asyncFindByName(String name) {
    return spreadOutPort
            .asyncFindByName(name)
            .switchIfEmpty(Mono.error(new GlobalException("WNE-HRS-SPREAD-0001", name)));
  }

  @Override
  @Transactional(transactionManager = "mongoTransactionManager")
  public Spread saveSpreadTemplate(SpreadSaveCommand spreadSaveCommand) {
    Spread spread = Spread.withoutId(spreadSaveCommand);
    Spread result = Optional
            .ofNullable(spreadOutPort.saveTemplate(spread))
            .orElseThrow(() -> new GlobalException("WNE-HRS-SPREAD-0001", spread.getName()));
//    if (true){
//      throw new GlobalException("WNE-HRS-SAMPLE-0001", spread.getName());
//    }
    return result;
  }

  @Override
  @Transactional(transactionManager = "mongoTransactionManager")
  public Spread saveSpread2(SpreadSaveCommand spreadSaveCommand) {
    Spread spread = Spread.withoutId(spreadSaveCommand);
    return Optional
            .ofNullable(spreadTypeOfOutPort.saveSpreadTyeOf(spread))
            .orElseThrow(() -> new GlobalException("WNE-HRS-SPREAD-0001", spread.getName()));
  }

  @Override
  public Spread findByName2(String name) {
    return Optional
            .ofNullable(spreadTypeOfOutPort.findByNameTypeOf(name))
            .orElseThrow(() -> new GlobalException("WNE-HRS-SPREAD-0001", name));
  }

  @Override
  public Mono<Spread> asyncFindByName2(String name) {
    return spreadTypeOfOutPort
            .asyncTypeOfFindByName(name)
            .switchIfEmpty(Mono.error(new GlobalException("WNE-HRS-SPREAD-0001", name)));
  }
}
