package prototype.hexa.sample.adapter.in.web.sample.nodejs;

import lombok.Builder;

@Builder
record NodeSampleRulesQueryResponse(String name, NodeRulesResponse ruleResult) {
  @Builder
  record NodeRulesResponse (int index, long result1, long result2, long result3, CalcResultData result4) {
  }
  @Builder
  record CalcResultData(long sum, long minus, long multi){
  }
}
