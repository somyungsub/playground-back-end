package prototype.hexa.sample.application.port.out.spread;

import prototype.hexa.sample.domain.spread.Spread;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface SpreadTypeOfOutPort {
  Spread saveSpreadTyeOf(Spread spread);
  Spread findByNameTypeOf(String name);
  Mono<Spread> asyncTypeOfFindByName(String name);
}
