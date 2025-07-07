package prototype.hexa.sample.adapter.in.web.sample.mongo;

import prototype.hexa.sample.domain.sample.constant.SampleCode;

import java.util.List;

record SampleSaveRequest(String name, SampleCode code, List<SaveInput> inputs ) {
  record SaveInput(String name, String value) {
  }
}
