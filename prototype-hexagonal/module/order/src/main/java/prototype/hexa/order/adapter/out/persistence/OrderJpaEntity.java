package prototype.hexa.order.adapter.out.persistence;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prototype.hexa.order.domain.Order;
import prototype.hexa.order.domain.vo.Payment;

import javax.persistence.*;

@Entity
@Table(name = "ORDER")
@Getter
@NoArgsConstructor
@AllArgsConstructor
class OrderJpaEntity {
  @Id
  @GeneratedValue
  private long id;
  @Column(name = "ORDER_STATUS")
  private String orderStatus;
  @Column(name = "TOTAL_PRICE")
  private int totalPrice;
  @Embedded
  private PaymentEmbed payment;
  @Column(name = "member_id")
  private long memberId;

  static OrderJpaEntity of(Order order, long memberId) {
    return new OrderJpaEntity(
            order.getId(),
            order.getOrderStatus().name(),
            order.getTotalPrice(),
            PaymentEmbed.of(order.getPayment()),
            memberId
    );
  }

  @Getter
  @Embeddable
  @AllArgsConstructor
  @NoArgsConstructor
  static class PaymentEmbed {
    private String paymentMethod;
    private boolean isPaid;
    static PaymentEmbed of(Payment payment) {
//      return new PaymentEmbed("", false);
      return new PaymentEmbed(
              payment.paymentMethod().name(),
              payment.isPaid()
      );
    }
  }
}
