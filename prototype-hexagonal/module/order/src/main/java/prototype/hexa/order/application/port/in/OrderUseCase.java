package prototype.hexa.order.application.port.in;

import prototype.hexa.common.port.in.QueryUseCase;
import prototype.hexa.order.application.port.in.command.ProductOrderCommand;
import prototype.hexa.order.domain.Order;

public interface OrderUseCase extends QueryUseCase<Order, Long> {
  Order orderProduct(ProductOrderCommand productOrderCommand);
  Order findByIdWithMember(long orderId, long memberId);
}
