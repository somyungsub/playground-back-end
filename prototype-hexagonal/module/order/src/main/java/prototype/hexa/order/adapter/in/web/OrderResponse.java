package prototype.hexa.order.adapter.in.web;

import lombok.Builder;
import prototype.hexa.order.domain.Order;

@Builder
record OrderResponse(
        Order order,
        String testName
) {
}
