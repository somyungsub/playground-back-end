package prototype.hexa.sample.domain.sample;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import prototype.hexa.sample.application.port.in.sample.command.SampleSaveCommand;
import prototype.hexa.sample.application.port.in.sample.command.SampleUpdateCommand;
import prototype.hexa.sample.domain.sample.constant.SampleCode;
import prototype.hexa.sample.domain.spread.vo.Row;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Value
@Builder
public class Sample {
  Long id;
  String name;
  SampleCode code;
  List<SampleInput> inputs;
//  @JsonCreator
//  public Sample(@JsonProperty("id") Long id,
//                @JsonProperty("name") String name,
//                @JsonProperty("code") SampleCode code,
//                @JsonProperty("inputs") List<SampleInput> inputs) {
//    this.id = id;
//    this.name = name;
//    this.code = code;
//    this.inputs = inputs;
//  }
  public static Sample withId(Long id, String name, SampleCode code, List<SampleInput> inputs) {
    return builder()
            .id(id)
            .name(name)
            .code(code)
            .inputs(inputs)
            .build();
  }


  public static Sample withoutId(SampleSaveCommand sampleSaveCommand) {
    return withId(
            null,
            sampleSaveCommand.getName(),
            sampleSaveCommand.getCode(),
            sampleSaveCommand.getInputs()
    );
  }

  public void validation() {
    checkArgument(ObjectUtils.isNotEmpty(id), "sampleId 은 비어 있으면 안됩니다.");
    checkArgument(StringUtils.isNotEmpty(name), "name 은 비어 있으면 안됩니다.");
  }

  public Sample withUpdate(SampleUpdateCommand sampleUpdateCommand) {
    return withId(
      this.id,
      sampleUpdateCommand.getName(),
      sampleUpdateCommand.getCode(),
      sampleUpdateCommand.getInputs()
//      this.inputs
    );
  }
}
