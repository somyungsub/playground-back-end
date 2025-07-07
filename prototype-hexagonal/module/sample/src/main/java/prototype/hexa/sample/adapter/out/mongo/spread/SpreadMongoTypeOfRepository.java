package prototype.hexa.sample.adapter.out.mongo.spread;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface SpreadMongoTypeOfRepository extends MongoRepository<SpreadTypeOfDocument, String> {
  Optional<SpreadTypeOfDocument> findByName(String name);
}