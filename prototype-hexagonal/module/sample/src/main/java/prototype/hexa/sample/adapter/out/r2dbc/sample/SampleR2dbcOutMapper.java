package prototype.hexa.sample.adapter.out.r2dbc.sample;

import org.springframework.stereotype.Component;
import prototype.hexa.sample.domain.sample.Sample;
import prototype.hexa.sample.domain.sample.SampleInput;
import prototype.hexa.sample.domain.sample.constant.SampleCode;

import java.util.List;


@Component
class SampleR2dbcOutMapper {

  Sample toDomain(SampleR2dbc sampleR2dbc, List<SampleInputR2dbc> inputs) {
    List<SampleInput> sampleInputs = inputs.stream().map(input -> SampleInput.withId(input.getId(), input.getName(), input.getValue())).toList();
    return Sample.withId(
      sampleR2dbc.getId(),
      sampleR2dbc.getName(),
      SampleCode.valueOf(sampleR2dbc.getCode()),
      sampleInputs
    );
  }

  SampleR2dbc toEntity(Sample sample) {
    return SampleR2dbc.withoutId(
      sample.getName(),
      sample.getCode().name()
    );
  }

  List<SampleInputR2dbc> toEntityInputs(List<SampleInput> inputs, Long sampleId) {
    return inputs.stream()
      .map(sampleInput -> SampleInputR2dbc.withoutId(sampleInput.getName(), sampleInput.getValue(), sampleId))
      .toList();

  }
}
