package prototype.hexa.sample.adapter.out.persistence.sample.association;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SAMPLE_INPUT")
@Getter
@AllArgsConstructor
@NoArgsConstructor
class SampleInputEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "INPUT_NAME")
    private String name;
    @Column(name = "INPUT_VALUE")
    private String value;
    @ManyToOne
    @JoinColumn(name = "SAMPLE_ID")
    private SampleJpaEntity sampleJpaEntity;

    static SampleInputEntity of(Long id, String name, String value) {
        return new SampleInputEntity(id, name, value, null);
    }

    void setSampleJpaEntity(SampleJpaEntity sampleJpaEntity) {
        this.sampleJpaEntity = sampleJpaEntity;
    }

}
