package prototype.hexa.sample.adapter.out.persistence.sample.association;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prototype.hexa.sample.domain.sample.SampleInput;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SAMPLE")
@Getter
@AllArgsConstructor
@NoArgsConstructor
class SampleJpaEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "code")
    private String code;
    @OneToMany(mappedBy = "sampleJpaEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SampleInputEntity> inputEntities = new ArrayList<>();

    static SampleJpaEntity of(Long id, String name, String code, List<SampleInput> sampleInputEntityList) {
        SampleJpaEntity sampleJpaEntity = new SampleJpaEntity(id, name, code, new ArrayList<>());
        sampleJpaEntity.addInputList(sampleInputEntityList);
        return sampleJpaEntity;
    }

    private void addInput(SampleInputEntity sampleInput) {
        this.inputEntities.add(sampleInput);
        sampleInput.setSampleJpaEntity(this);
    }

    private void addInputList(List<SampleInput> inputList) {
        inputList.stream()
                .map(input -> SampleInputEntity.of(input.getId(), input.getName(), input.getValue()))
                .forEach(this::addInput);
    }
}
