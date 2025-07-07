package prototype.hexa.sample.adapter.in.web.spread;

import org.springframework.stereotype.Component;
import prototype.hexa.sample.application.port.in.spread.command.SpreadSaveCommand;
import prototype.hexa.sample.domain.spread.Spread;

@Component
class SpreadWebMapper {
  SpreadSaveCommand toSaveCommand(SpreadSaveRequest request) {
    return SpreadSaveCommand.builder()
            .name(request.name())
            .rows(request.rows())
            .build();
  }

  SpreadQueryResponse toQueryResponse(Spread spread) {
    return SpreadQueryResponse.builder()
            .spread(spread)
            .build();
  }
}
