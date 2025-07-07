package prototype.hexa.sample.adapter.in.web.sample.mongo;

import org.springframework.stereotype.Component;
import prototype.hexa.sample.application.port.in.sample.command.SampleSaveCommand;
import prototype.hexa.sample.application.port.in.sample.command.SampleUpdateCommand;
import prototype.hexa.sample.domain.sample.Sample;
import prototype.hexa.sample.domain.sample.SampleInput;

import java.util.Collections;
import java.util.List;

@Component
class MongoWebMapper {
    SampleSaveCommand toCommand(SampleSaveRequest request) {
        List<SampleInput> inputs = request.inputs().stream()
                .map(saveInput -> SampleInput.withoutId(saveInput.name(), saveInput.value()))
                .toList();

        return SampleSaveCommand.withoutId(
                request.name(),
                request.code(),
                inputs
        );
    }

    SampleQueryResponse toResponse(Sample result) {
        return SampleQueryResponse.builder()
                .id(result.getId())
                .name(result.getName())
                .build();
    }
}
