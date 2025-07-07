package prototype.hexa.sample.adapter.out.mongo.spread;

import org.springframework.data.mongodb.repository.MongoRepository;

interface RowMongoTypeOfRepository extends MongoRepository<RowTypeOfDocument, String> {
}