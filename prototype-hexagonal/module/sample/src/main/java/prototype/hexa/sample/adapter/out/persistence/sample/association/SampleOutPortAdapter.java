package prototype.hexa.sample.adapter.out.persistence.sample.association;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import prototype.hexa.common.annotation.PersistenceAdapter;
import prototype.hexa.sample.application.port.out.sample.SampleOutPort;
import prototype.hexa.sample.application.port.out.sample.SampleQueryDslOutPort;
import prototype.hexa.sample.domain.sample.Sample;

import java.util.Optional;

import static prototype.hexa.sample.adapter.out.persistence.sample.association.QSampleJpaEntity.sampleJpaEntity;


@PersistenceAdapter(value = "sampleOutPortAdapter")
@RequiredArgsConstructor
@Primary
class SampleOutPortAdapter implements SampleOutPort, SampleQueryDslOutPort {
    private final SampleRepository sampleRepository;
    private final SampleOutMapper sampleOutMapper;
    private final JPAQueryFactory queryFactory;

    @Override
    public Sample save(Sample sample) {
        SampleJpaEntity entity = sampleOutMapper.toEntity(sample);
        SampleJpaEntity save = sampleRepository.save(entity);
        return sampleOutMapper.toDomain(save);
    }

    @Override
    public Sample findById(Long sampleId) {
        return sampleRepository
                .findById(sampleId)
                .map(sampleOutMapper::toDomain)
                .orElse(null);
    }

    @Override
    public void delete(Long sampleId) {
        sampleRepository.deleteById(sampleId);
    }

    @Override
    public Sample update(Sample sample) {
        SampleJpaEntity entity = sampleOutMapper.toEntity(sample);
        SampleJpaEntity result = sampleRepository.save(entity);
        return sampleOutMapper.toDomain(result);
    }

    @Override
    public Sample findByIdDsl(Long sampleId) {
        JPAQuery<SampleJpaEntity> query = queryFactory
                .selectFrom(sampleJpaEntity)
                .where(
                        sampleJpaEntity.id.eq(sampleId)
                );
        SampleJpaEntity fetch = query.fetchOne();
        return sampleOutMapper.toDomain(fetch);
    }

}
