package prototype.hexa.sample.domain.spread.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Column {
  String name;
  String value;
}
