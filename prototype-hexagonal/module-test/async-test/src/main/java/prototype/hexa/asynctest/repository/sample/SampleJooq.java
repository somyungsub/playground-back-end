package prototype.hexa.asynctest.repository.sample;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "sample", schema = "sample")
@Getter
@AllArgsConstructor
class SampleJooq {
  @Id
  private final Long id;
  @Column("name")
  private final String name;
  @Column("code")
  private final String code;

}
