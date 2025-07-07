package prototype.hexa.sample.domain.spread;

import lombok.Builder;
import lombok.Value;
import prototype.hexa.sample.application.port.in.spread.command.SpreadSaveCommand;
import prototype.hexa.sample.domain.spread.vo.Row;

import java.util.List;

@Value
@Builder
public class Spread {
  String id;
  String name;
  List<Row> rows;
  public static Spread withoutId(SpreadSaveCommand spreadSaveCommand) {
    return builder()
            .id(null)
            .name(spreadSaveCommand.getName())
            .rows(spreadSaveCommand.getRows())
            .build();
  }
}
