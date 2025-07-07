package prototype.hexa.order.adapter.out.persistence;

import org.springframework.stereotype.Component;
import prototype.hexa.order.domain.Order;
import prototype.hexa.order.domain.enumeration.OrderStatus;
import prototype.hexa.order.domain.vo.Payment;

@Component
class OrderOutPortMapper {
  public OrderJpaEntity toEntity(Order order, Long memberId) {
    return OrderJpaEntity.of(order, memberId);
  }

  public Order toDomain(OrderJpaEntity entity) {
    Payment payment = Payment.of(Payment.PaymentMethod.valueOf(entity.getPayment().getPaymentMethod()), entity.getPayment().isPaid());
    return Order.builder()
            .id(entity.getId())
            .totalPrice(entity.getTotalPrice())
            .payment(payment)
            .orderStatus(OrderStatus.valueOf(entity.getOrderStatus()))
            .build()
            ;
  }
}
