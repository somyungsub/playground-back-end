package prototype.hexa.sample.adapter.out.persistence.sample.noassociation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "SAMPLE_INPUT_NO")
@Getter
@AllArgsConstructor
@NoArgsConstructor
class SampleInputNoAssociationJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "INPUT_NAME")
    private String name;
    @Column(name = "INPUT_VALUE")
    private String value;
    @Column(name = "sample_id")
    private Long sampleId;

    static SampleInputNoAssociationJpaEntity of(Long id, String name, String value, Long sampleId) {
        return new SampleInputNoAssociationJpaEntity(id, name, value, sampleId);
    }

}
