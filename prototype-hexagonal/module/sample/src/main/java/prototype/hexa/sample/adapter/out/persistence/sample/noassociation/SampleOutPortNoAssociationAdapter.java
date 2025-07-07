package prototype.hexa.sample.adapter.out.persistence.sample.noassociation;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import prototype.hexa.common.annotation.PersistenceAdapter;
import prototype.hexa.sample.application.port.out.sample.SampleOutPort;
import prototype.hexa.sample.application.port.out.sample.SampleQueryDslOutPort;
import prototype.hexa.sample.domain.sample.Sample;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@PersistenceAdapter(value = "sampleOutPortNoAssociationAdapter")
@RequiredArgsConstructor
class SampleOutPortNoAssociationAdapter implements SampleOutPort, SampleQueryDslOutPort {
  private final SampleNoAssociationRepository sampleNoAssociationRepository;
  private final SampleInputNoAssociationRepository sampleInputNoAssociationRepository;
  private final JdbcTemplate jdbcTemplate;
  private final SampleNoOutMapper sampleNoOutMapper;
  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Sample save(Sample sample) {
    SampleNoAssociationJpaEntity entity = sampleNoOutMapper.toEntity(sample);
    SampleNoAssociationJpaEntity save = sampleNoAssociationRepository.save(entity);
    List<SampleInputNoAssociationJpaEntity> list = sample.getInputs().stream().map(sampleInput -> sampleNoOutMapper.toInputEntity(sampleInput, save.getId())).toList();
    List<SampleInputNoAssociationJpaEntity> sampleInputNoAssociationJpaEntities = sampleInputNoAssociationRepository.saveAll(list);
    return sampleNoOutMapper.toDomain(save, sampleInputNoAssociationJpaEntities);
  }

  @Override
  public Sample findById(Long sampleId) {
    List<JoinSample> joinSamples = sampleNoAssociationRepository.joinSample(sampleId);
    List<SampleInputNoAssociationJpaEntity> list = joinSamples.stream()
      .map(JoinSample::getSampleInputNoAssociationJpaEntity)
      .toList();
    SampleNoAssociationJpaEntity sampleNoAssociationJpa = joinSamples.get(0).getSampleNoAssociationJpa();
    return sampleNoOutMapper.toDomain(sampleNoAssociationJpa, list);
  }

  @Override
  @Transactional
  public void delete(Long sampleId) {
    // sample_input_no 테이블설계 시 외래키 on delete cascade 설정 -> 자식들 같이 삭제되게
    int rowsAffected = jdbcTemplate.update("DELETE FROM sample.sample_no WHERE id = ?", sampleId);
    System.out.println("Rows affected: " + rowsAffected);

    // jpa-> 명시적 자식 삭제 후 부모삭제
//    sampleInputNoAssociationRepository.deleteBySampleId(sampleId);
//    sampleNoAssociationRepository.deleteById(sampleId);
  }

  @Override
  public Sample update(Sample sample) {
    SampleNoAssociationJpaEntity entity = sampleNoOutMapper.toEntity(sample);
    SampleNoAssociationJpaEntity save = sampleNoAssociationRepository.save(entity);
    List<SampleInputNoAssociationJpaEntity> list = sample.getInputs().stream()
      .map(sampleInput -> sampleNoOutMapper.toInputEntity(sampleInput, save.getId()))
      .toList();
    List<SampleInputNoAssociationJpaEntity> sampleInputNoAssociationJpaEntities = sampleInputNoAssociationRepository.saveAll(list);
    return sampleNoOutMapper.toDomain(save, sampleInputNoAssociationJpaEntities);
  }

  @Override
  public Sample findByIdDsl(Long sampleId) {
    QSampleNoAssociationJpaEntity sample = QSampleNoAssociationJpaEntity.sampleNoAssociationJpaEntity;
    QSampleInputNoAssociationJpaEntity sampleInput = QSampleInputNoAssociationJpaEntity.sampleInputNoAssociationJpaEntity;

    List<Tuple> results = jpaQueryFactory
      .select(sample, sampleInput)
      .from(sample)
      .leftJoin(sampleInput).on(sample.id.eq(sampleInput.sampleId))
      .where(sample.id.eq(sampleId))
      .fetch();

    if (results.isEmpty()) {
      return null;
    }

    return sampleNoOutMapper.toDomain(
            Objects.requireNonNull(results.get(0).get(sample)),
            results.stream().map(tuple -> tuple.get(sampleInput)).toList());
  }
}
