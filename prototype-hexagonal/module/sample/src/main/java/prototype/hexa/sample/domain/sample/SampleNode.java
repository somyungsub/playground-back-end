package prototype.hexa.sample.domain.sample;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import prototype.hexa.sample.domain.sample.vo.NodeResult;



@AllArgsConstructor
public class SampleNode {
  @Getter
  private final int index;
  private final SampleNodeId sampleNodeId;
  private final Sample sample;
  private final NodeResult nodeResult;

  public static SampleNode of(Sample sample, NodeResult nodeResult) {
    return new SampleNode(0, SampleNodeId.withId(sample.getId()), sample, nodeResult);
  }
  public static SampleNode of(int index, Sample sample, NodeResult nodeResult) {
    return new SampleNode(index, SampleNodeId.withId(sample.getId()) ,sample, nodeResult);
  }

  public String getName() {
    return sample.getName();
  }

  public long getResult1() {
    return nodeResult.getResult1();
  }
//
  public long getResult2() {
    return nodeResult.getResult2();
  }
//
  public long getResult3() {
    return nodeResult.getResult3();
  }

  public long getSum() {
    return nodeResult.getSum();
  }
  public long getMinus() {
    return nodeResult.getMinus();
  }
  public long getMulti() {
    return nodeResult.getSum();
  }

  @Value
  private static class SampleNodeId {
    Long id;
    static SampleNodeId withNoId() {
      return new SampleNodeId(null);
    }
    static SampleNodeId withId(Long id) {
      return new SampleNodeId(id);
    }
  }
}
