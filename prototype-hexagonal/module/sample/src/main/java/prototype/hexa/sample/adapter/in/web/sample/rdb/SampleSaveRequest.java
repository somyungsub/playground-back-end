package prototype.hexa.sample.adapter.in.web.sample.rdb;

import prototype.hexa.sample.domain.sample.constant.SampleCode;

import java.util.List;

record SampleSaveRequest(String name, SampleCode code, List<SampleInputRequest> inputs) {
  record SampleInputRequest(String name, String value) {
  }
}
