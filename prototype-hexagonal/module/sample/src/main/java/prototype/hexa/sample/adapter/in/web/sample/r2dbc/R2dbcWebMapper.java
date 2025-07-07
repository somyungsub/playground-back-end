package prototype.hexa.sample.adapter.in.web.sample.r2dbc;

import org.springframework.stereotype.Component;
import prototype.hexa.sample.application.port.in.sample.command.SampleSaveCommand;
import prototype.hexa.sample.domain.sample.Sample;
import prototype.hexa.sample.domain.sample.SampleInput;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
class R2dbcWebMapper {
    SampleSaveCommand toCommand(SampleSaveRequest sampleSaveRequest) {
        List<SampleInput> inputs = sampleSaveRequest.inputs().stream()
                .map(input -> SampleInput.withId(null, input.name(), input.value()))
                .collect(toList());
        return SampleSaveCommand.withoutId(sampleSaveRequest.name(), sampleSaveRequest.code(), inputs);
    }

    SampleQueryResponse toResponse(Sample result) {
        return new SampleQueryResponse(
          result.getName(),
          result.getCode(),
          result.getInputs()
        );
    }
}
