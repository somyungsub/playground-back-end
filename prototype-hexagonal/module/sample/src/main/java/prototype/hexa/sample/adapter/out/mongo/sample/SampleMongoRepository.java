package prototype.hexa.sample.adapter.out.mongo.sample;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

interface SampleMongoRepository extends MongoRepository<SampleDocument, String> {
    List<SampleDocument> findByName(String name);
    SampleDocument findBySampleId(Long sampleId);
//    List<SampleDocument> findByInputEntities_Name(String inputName);
}
