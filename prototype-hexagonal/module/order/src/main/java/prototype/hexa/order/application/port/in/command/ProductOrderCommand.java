package prototype.hexa.order.application.port.in.command;

import lombok.Builder;
import lombok.Value;
import prototype.hexa.order.domain.enumeration.OrderStatus;
import prototype.hexa.order.domain.vo.Payment;

@Value
@Builder
public class ProductOrderCommand {
  OrderStatus orderStatus;
  int totalPrice;
  Payment payment;
  long memberId;

  // TODO
  private void validateOrder() {

  }
}
