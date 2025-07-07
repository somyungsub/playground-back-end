package prototype.hexa.sample.adapter.in.web.sample.r2dbc;

import prototype.hexa.sample.domain.sample.constant.SampleCode;

import java.util.List;

record SampleSaveRequest(Long id, String name, SampleCode code, List<SampleInputRequest> inputs) {
    record SampleInputRequest(String name, String value) {
    }
}
