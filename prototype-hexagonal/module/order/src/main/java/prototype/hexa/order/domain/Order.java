package prototype.hexa.order.domain;

import lombok.Builder;
import lombok.Value;
import prototype.hexa.membership.domain.model.Member;
import prototype.hexa.order.application.port.in.command.ProductOrderCommand;
import prototype.hexa.order.domain.enumeration.OrderStatus;
import prototype.hexa.order.domain.vo.Payment;

@Value
@Builder
public class Order {
  long id;
  OrderStatus orderStatus;
  int totalPrice;
  Payment payment;
  Member member;

  private static void validateOrder() {
    // TODO 비즈니스 명세,유효성검증
  }

  public static Order withoutId(ProductOrderCommand command) {
    validateOrder();
    return builder()
            .id(0L)
            .orderStatus(command.getOrderStatus())
            .totalPrice(command.getTotalPrice())
            .payment(command.getPayment())
            .build()
            ;
  }

  public static Order withId() {
    validateOrder();
    return null;
  }

  public Order withMember(Member member) {
    validateOrder();
    return builder()
            .id(this.id)
            .orderStatus(this.orderStatus)
            .totalPrice(this.totalPrice)
            .payment(this.payment)
            .member(member)
            .build();
  }
}
