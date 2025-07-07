package prototype.hexa.sample.application.port.in.sample.command;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import prototype.hexa.common.validation.SelfValidating;
import prototype.hexa.sample.domain.sample.SampleInput;
import prototype.hexa.sample.domain.sample.constant.SampleCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Value
@Builder
@EqualsAndHashCode(callSuper = false)
public class SampleUpdateCommand extends SelfValidating<SampleUpdateCommand> {
    @NotBlank(message = "name은 null 이거나 비어있으면 안됩니다")
    String name;
    SampleCode code;
    @NotNull
    List<SampleInput> inputs;

    private SampleUpdateCommand(String name, SampleCode code, List<SampleInput> inputs) {
        this.name = name;
        this.code = code;
        this.inputs = inputs;
        validateSelf();
    }
}
