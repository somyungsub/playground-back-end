package prototype.hexa.sample.application.port.in.sample.command;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Delegate;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import prototype.hexa.sample.domain.sample.SampleInput;
import prototype.hexa.sample.domain.sample.constant.SampleCode;

import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;


@Value
@Builder
@EqualsAndHashCode(callSuper = false)
public class SampleSaveCommand {
    Long id;
    String name;
    SampleCode code;
    @Delegate
    List<SampleInput> inputs;

    public static SampleSaveCommand withoutId(String name, SampleCode code, List<SampleInput> inputs) {
        validationParam(name, code);
        return withId(null, name, code, inputs);
    }
    public static SampleSaveCommand withId(Long id, String name, SampleCode code, List<SampleInput> inputs) {
        validationParam(name, code);
        return builder()
                .id(id)
                .name(name)
                .code(code)
                .inputs(inputs)
                .build();
    }

    private static void validationParam(String name, SampleCode code) {
        checkArgument(ObjectUtils.isNotEmpty(code), StringUtils.join("code 은 비어 있으면 안됩니다. -> ", Arrays.stream(SampleCode.values()).map(SampleCode::name)));
        checkArgument(StringUtils.isNotEmpty(name), "name 은 비어 있으면 안됩니다.");
    }

}
