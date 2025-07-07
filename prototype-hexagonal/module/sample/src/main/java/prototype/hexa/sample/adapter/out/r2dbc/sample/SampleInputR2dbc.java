package prototype.hexa.sample.adapter.out.r2dbc.sample;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Table(name = "sample_input_r2dbc")
@Getter
@AllArgsConstructor
@NoArgsConstructor
class SampleInputR2dbc {
    @Id
    @GeneratedValue
    private Long id;
    @Column("INPUT_NAME")
    private String name;
    @Column("INPUT_VALUE")
    private String value;
    @Column("SAMPLE_ID")
    private Long sampleId;

    static SampleInputR2dbc withoutId(String name, String value, Long sampleId) {
        return new SampleInputR2dbc(null, name, value, sampleId);
    }

    static SampleInputR2dbc withId(Long id, String name, String value, Long sampleId) {
        return new SampleInputR2dbc(id, name, value, sampleId);
    }
}
