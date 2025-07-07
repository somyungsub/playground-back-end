package prototype.hexa.sample.application.port.in.sample.command;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import prototype.hexa.common.validation.SelfValidating;
import prototype.hexa.sample.domain.sample.SampleInput;

import javax.validation.constraints.NotNull;
import java.util.List;


@Value
@Builder
@EqualsAndHashCode(callSuper = false)
public class SampleSaveCommand_backup extends SelfValidating<SampleSaveCommand_backup> {
    Long id;
    @NotNull(message = "name은 null 이면 안됩니다")
    String name;
    List<SampleInput> inputs;

    private SampleSaveCommand_backup(Long id, String name, List<SampleInput> inputs) {
        this.id = id;
        this.name = name;
        this.inputs = inputs;
        validateSelf();
    }

    public static SampleSaveCommand_backup of(String name, List<SampleInput> inputs) {
        return withId(null, name, inputs);
    }
    public static SampleSaveCommand_backup withId(Long id, String name, List<SampleInput> inputs) {
        return builder()
                .id(id)
                .name(name)
                .inputs(inputs)
                .build();
    }
}
