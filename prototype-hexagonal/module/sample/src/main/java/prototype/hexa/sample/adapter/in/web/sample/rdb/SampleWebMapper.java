package prototype.hexa.sample.adapter.in.web.sample.rdb;

import org.springframework.stereotype.Component;
import prototype.hexa.sample.application.port.in.sample.command.SampleSaveCommand;
import prototype.hexa.sample.domain.sample.Sample;
import prototype.hexa.sample.domain.sample.SampleInput;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
class SampleWebMapper {
    SampleSaveCommand toCommand(SampleSaveRequest sampleSaveRequest) {
        List<SampleInput> inputs = sampleSaveRequest.inputs().stream()
                .map(input -> SampleInput.withId(null, input.name(), input.value()))
                .collect(toList());
        return SampleSaveCommand.withoutId(
                sampleSaveRequest.name(),
                sampleSaveRequest.code(),
                inputs
        );
    }

    SampleResponse toResponse(Sample result) {
        return SampleResponse.builder()
                .name(result.getName())
                .code(result.getCode().name())
                .inputs(result.getInputs())
                .build();
    }
}
