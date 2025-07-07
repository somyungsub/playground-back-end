package prototype.hexa.sample.application.port.out.spread;

import prototype.hexa.sample.domain.spread.Spread;
import reactor.core.publisher.Mono;

public interface SpreadOutPort {
  Spread save(Spread spread);
  Spread saveTemplate(Spread spread);
  Spread findByName(String name);
  Mono<Spread> asyncFindByName(String name);
}
