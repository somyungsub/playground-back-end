package prototype.hexa.sample.adapter.out.mongo.spread;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

interface SpreadMongoTypeOfReactiveRepository extends ReactiveMongoRepository<SpreadTypeOfDocument, String> {
  Mono<SpreadTypeOfDocument> findByName(String name);
}
