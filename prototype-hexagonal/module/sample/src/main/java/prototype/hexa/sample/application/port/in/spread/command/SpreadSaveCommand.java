package prototype.hexa.sample.application.port.in.spread.command;

import lombok.Builder;
import lombok.Value;
import prototype.hexa.common.validation.SelfBuilderValidating;
import prototype.hexa.common.validation.SelfValidating;
import prototype.hexa.sample.domain.spread.vo.Row;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Value
@Builder(builderClassName = "SpreadSaveCommandValidator")
//public class SpreadSaveCommand extends SelfBuilderValidating<SpreadSaveCommand> {
public class SpreadSaveCommand extends SelfValidating<SpreadSaveCommand> {
  @NotEmpty(message = "Name must not be empty")
  String name;
  List<Row> rows;

  public static class SpreadSaveCommandValidator {
    public SpreadSaveCommand build() {
      SpreadSaveCommand saveCommand = new SpreadSaveCommand(name, rows);
      saveCommand.validateSelf();
      return saveCommand;
    }
  }


//  @Override
//  protected SpreadSaveCommand buildInternal() {
//    return new SpreadSaveCommand(name, rows);
//  }
//  // 유효성 검사를 포함한 build 메서드
//  public SpreadSaveCommand build(String name, List<Row> rows) {
//    SpreadSaveCommand command = SpreadSaveCommand.builder()
//            .name(name)
//            .rows(rows)
//            .build();
//    validate(command);
//    // 유효성 검사
//    return command;
//  }

}
