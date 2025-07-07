package prototype.hexa.order.application.port.out;

import prototype.hexa.common.port.out.QueryOutPort;
import prototype.hexa.order.domain.Order;

public interface OrderOutPort extends QueryOutPort<Order, Long> {
  Order order(Order order, long memberId);
}
