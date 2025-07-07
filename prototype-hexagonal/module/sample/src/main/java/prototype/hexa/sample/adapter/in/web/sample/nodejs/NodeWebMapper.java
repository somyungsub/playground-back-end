package prototype.hexa.sample.adapter.in.web.sample.nodejs;

import org.springframework.stereotype.Component;
import prototype.hexa.sample.domain.sample.SampleNode;

import java.util.List;

import static java.util.stream.Collectors.*;

@Component
class NodeWebMapper {
  NodeSampleRulesQueryResponse toResponse(SampleNode sampleNode) {
    NodeSampleRulesQueryResponse.CalcResultData calcResult = NodeSampleRulesQueryResponse.CalcResultData.builder()
            .sum(sampleNode.getSum())
            .minus(sampleNode.getMinus())
            .multi(sampleNode.getMulti())
            .build();

    NodeSampleRulesQueryResponse.NodeRulesResponse nodeRulesResponse = NodeSampleRulesQueryResponse.NodeRulesResponse.builder()
            .index(sampleNode.getIndex())
            .result1(sampleNode.getResult1())
            .result2(sampleNode.getResult2())
            .result3(sampleNode.getResult3())
            .result4(calcResult).build();

    return NodeSampleRulesQueryResponse.builder()
            .name(sampleNode.getName())
            .ruleResult(nodeRulesResponse)
            .build();
  }

  List<NodeSampleRulesQueryResponse> toResponse(List<SampleNode> samples) {
    return samples.stream()
            .map(this::toResponse)
            .collect(toList());
  }
}
