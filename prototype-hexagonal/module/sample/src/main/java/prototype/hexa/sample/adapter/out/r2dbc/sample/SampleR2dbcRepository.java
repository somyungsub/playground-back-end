package prototype.hexa.sample.adapter.out.r2dbc.sample;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

interface SampleR2dbcRepository extends R2dbcRepository<SampleR2dbc, Long> {
//interface SampleR2dbcRepository extends ReactiveCrudRepository<SampleR2dbc, Long> {
  Mono<SampleR2dbc> findByName(String name);
}
