package prototype.hexa.order.domain.enumeration;

public enum OrderStatus {
  PROCESSING("처리중"),
  SHIPPED("배송중"),
  COMPLETED("완료"),
  CANCELLED("취소")
  ;

  private final String name;
  OrderStatus(String name) {
    this.name = name;
  }
}
