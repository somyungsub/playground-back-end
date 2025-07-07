package prototype.hexa.sample.adapter.out.persistence.sample.noassociation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "SAMPLE_NO")
@Getter
@AllArgsConstructor
@NoArgsConstructor
class SampleNoAssociationJpaEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "code")
    private String code;

    public static SampleNoAssociationJpaEntity of(Long id, String name, String code) {
        return new SampleNoAssociationJpaEntity(id, name, code);
    }
}
