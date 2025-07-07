package prototype.hexa.order.adapter.in.web;

import org.springframework.stereotype.Component;
import prototype.hexa.order.application.port.in.command.ProductOrderCommand;
import prototype.hexa.order.domain.Order;
import prototype.hexa.order.domain.enumeration.OrderStatus;
import prototype.hexa.order.domain.vo.Payment;

@Component
class OrderWebMapper {
  ProductOrderCommand toCommand(OrderRequest request) {
    return ProductOrderCommand.builder()
            .orderStatus(OrderStatus.PROCESSING)
            .payment(Payment.of(request.paymentMethod(), false))
            .totalPrice(request.totalPrice())
            .memberId(request.memberId())
            .build();
  }

  OrderResponse toResponse(Order order) {
    return OrderResponse.builder()
            .order(order)
            .testName("test~~~")
            .build();
  }
}
