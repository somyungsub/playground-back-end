package prototype.hexa.sample.adapter.out.mongo.spread;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface SpreadMongoRepository extends MongoRepository<SpreadDocument, String> {
  Optional<SpreadDocument> findByName(String name);
}
