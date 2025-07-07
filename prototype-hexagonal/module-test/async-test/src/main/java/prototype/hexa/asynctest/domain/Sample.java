package prototype.hexa.asynctest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.jooq.Record;

import java.util.List;

@Builder
@AllArgsConstructor
@Value
public class Sample {
  long id;
  String name;
  String code;
  List<SampleInput> inputs;

  public static Object of(Record record) {
    return null;
  }
}
