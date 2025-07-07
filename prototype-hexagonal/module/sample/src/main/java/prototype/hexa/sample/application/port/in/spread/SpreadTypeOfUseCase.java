package prototype.hexa.sample.application.port.in.spread;

import prototype.hexa.sample.application.port.in.spread.command.SpreadSaveCommand;
import prototype.hexa.sample.domain.spread.Spread;
import reactor.core.publisher.Mono;

public interface SpreadTypeOfUseCase {
  Spread saveSpread2(SpreadSaveCommand spreadSaveCommand);
  Spread findByName2(String name);
  Mono<Spread> asyncFindByName2(String name);
}
