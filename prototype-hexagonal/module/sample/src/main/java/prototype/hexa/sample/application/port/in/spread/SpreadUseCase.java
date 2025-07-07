package prototype.hexa.sample.application.port.in.spread;

import prototype.hexa.sample.application.port.in.spread.command.SpreadSaveCommand;
import prototype.hexa.sample.domain.spread.Spread;
import reactor.core.publisher.Mono;

public interface SpreadUseCase {
  Spread saveSpread(SpreadSaveCommand spreadSaveCommand);

  Spread findByName(String name);

  Mono<Spread> asyncFindByName(String name);

  Spread saveSpreadTemplate(SpreadSaveCommand spreadSaveCommand);
}
