package prototype.hexa.sample.adapter.in.web.spread;

import lombok.Builder;
import prototype.hexa.sample.domain.spread.Spread;

@Builder
record SpreadQueryResponse(Spread spread) {
}
