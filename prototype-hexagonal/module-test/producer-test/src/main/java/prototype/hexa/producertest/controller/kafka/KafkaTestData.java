package prototype.hexa.producertest.controller.kafka;

import lombok.Builder;

import java.util.List;

@Builder
public record KafkaTestData(
  String name,
  String message,
  int age,
  List<InnerData> innerDatas
) {
  @Builder
  record InnerData(
    String value,
    int index
  ) {
  }
}
