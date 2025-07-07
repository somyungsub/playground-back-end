package prototype.hexa.sample.adapter.out.r2dbc.sample;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Table(name = "sample_r2dbc")
@Getter
@AllArgsConstructor
@NoArgsConstructor
class SampleR2dbc {
    @Id
    @GeneratedValue
    private Long id;
    @Column("name")
    private String name;
    @Column("code")
    private String code;
    static SampleR2dbc withId(Long id, String name, String code) {
        return new SampleR2dbc(id, name, code);
    }

    static SampleR2dbc withoutId(String name, String code) {
        return new SampleR2dbc(null, name, code);
    }

}
