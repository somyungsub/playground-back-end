package prototype.hexa.sample.domain.spread.event;

import lombok.Builder;
import prototype.hexa.sample.domain.spread.Spread;

@Builder
public record SpreadEvent(
        String name,
        String eventName,
        String topicName,
        Spread spread) {
}
