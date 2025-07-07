package prototype.hexa.asynctest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder
@AllArgsConstructor
@Value
public class SampleInput {
  Long id;
  String name;
  String value;
}
