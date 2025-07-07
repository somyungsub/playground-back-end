package prototype.hexa.sample.domain.spread.vo;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Row {
  int index;
  List<Column> columns;
}
