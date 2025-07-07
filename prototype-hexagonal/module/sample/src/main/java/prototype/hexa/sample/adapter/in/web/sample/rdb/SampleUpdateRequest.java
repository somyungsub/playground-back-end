package prototype.hexa.sample.adapter.in.web.sample.rdb;

import prototype.hexa.sample.domain.sample.SampleInput;
import prototype.hexa.sample.domain.sample.constant.SampleCode;

import java.util.List;

public record SampleUpdateRequest(String name, SampleCode code, List<SampleInput> inputs) {
}
