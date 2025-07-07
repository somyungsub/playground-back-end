package prototype.hexa.order.domain.vo;

public record Payment(
        PaymentMethod paymentMethod,
        boolean isPaid  // 결제 완료 여부
) {
  public static Payment of(PaymentMethod paymentMethod, boolean paid) {
    return new Payment(paymentMethod,paid);
  }

  public enum PaymentMethod {
    CARD("카드"),
    CASH("현금"),
    TRANSFER("계좌이체");

    private final String paymentMethodName;

    PaymentMethod(String paymentMethodName) {
      this.paymentMethodName = paymentMethodName;
    }
  }
}
