package prototype.hexa.sample.adapter.out.r2dbc.sample;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

interface SampleInputR2dbcRepository extends R2dbcRepository<SampleInputR2dbc, Long> {
  Flux<SampleInputR2dbc> findBySampleId(Long sampleR2dbcId);
}

//interface SampleInputR2dbcRepository extends ReactiveCrudRepository<SampleInputR2dbc, Long> {
// sampleId로 자식 엔티티들을 조회
//  @Query("SELECT * FROM sample_r2dbc s JOIN sample_input_r2dbc i ON s.id = i.sample_id WHERE s.id = :sampleId")
//  Flux<SampleInputR2dbc> findSampleWithInputs(Long sampleId);
//}
