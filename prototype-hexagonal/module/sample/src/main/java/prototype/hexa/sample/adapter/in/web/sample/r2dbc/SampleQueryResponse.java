package prototype.hexa.sample.adapter.in.web.sample.r2dbc;

import prototype.hexa.sample.domain.sample.SampleInput;
import prototype.hexa.sample.domain.sample.constant.SampleCode;

import java.util.List;

record SampleQueryResponse(String name, SampleCode code, List<SampleInput> inputs) {
}
