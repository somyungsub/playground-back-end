package prototype.hexa.order.adapter.in.web;

import prototype.hexa.order.domain.enumeration.OrderStatus;
import prototype.hexa.order.domain.vo.Payment;

record OrderRequest(
//        OrderStatus orderStatus,
        int totalPrice,
        Payment.PaymentMethod paymentMethod,
        long memberId
) {
}
