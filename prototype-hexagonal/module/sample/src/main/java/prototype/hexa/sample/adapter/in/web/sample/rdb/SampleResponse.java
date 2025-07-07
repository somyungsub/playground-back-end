package prototype.hexa.sample.adapter.in.web.sample.rdb;

import lombok.Builder;
import prototype.hexa.sample.domain.sample.SampleInput;

import java.util.List;

@Builder
record SampleResponse(String name, String code, List<SampleInput> inputs) {
}
