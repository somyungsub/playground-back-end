package prototype.hexa.asynctest.repository.sample;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "sample_input", schema = "sample")
@Getter
@AllArgsConstructor
public class SampleJooqInput {
  @Id
  private final Long id;
  @Column("name")
  private final String name;
  @Column("value")
  private final String value;
  @Column("sample_id")
  private final Long sampleId;
}
