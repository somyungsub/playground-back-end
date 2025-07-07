package prototype.hexa.sample.adapter.out.mongo.spread;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

interface SpreadMongoReactiveRepository extends ReactiveMongoRepository<SpreadDocument, String> {
  Mono<SpreadDocument> findByName(String name);
}
