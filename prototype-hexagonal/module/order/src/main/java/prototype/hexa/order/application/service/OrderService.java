package prototype.hexa.order.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import prototype.hexa.common.annotation.UseCase;
import prototype.hexa.common.exception.GlobalException;
import prototype.hexa.membership.application.port.out.MemberShipOutPort;
import prototype.hexa.membership.domain.model.Member;
import prototype.hexa.order.application.port.in.OrderUseCase;
import prototype.hexa.order.application.port.in.command.ProductOrderCommand;
import prototype.hexa.order.application.port.out.OrderOutPort;
import prototype.hexa.order.domain.Order;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {
  private final OrderOutPort orderOutPort;
  private final MemberShipOutPort memberShipOutPort;

  @Override
  @Transactional
  public Order orderProduct(ProductOrderCommand productOrderCommand) {
    Order order = Order.withoutId(productOrderCommand);
    return Optional
            .ofNullable(orderOutPort.order(order, productOrderCommand.getMemberId()))
            .map(resultOrder -> {
              Member member = findMember(productOrderCommand.getMemberId());
              // TODO 결제, 배송 등 필요에따라 후처리 -> event
              // TODO domain service -> ?
              return resultOrder.withMember(member);
            })
            .orElseThrow(()-> new GlobalException("WNE-ORDER-0001", productOrderCommand.getMemberId()));
  }

  @Override
  public Order findByIdWithMember(long orderId, long memberId) {
    return Optional
            .ofNullable(orderOutPort.findById(orderId))
            .map(resultOrder -> {
              Member member = findMember(memberId);
              return resultOrder.withMember(member);
            })
            .orElseThrow(()-> new GlobalException("WNE-ORDER-0001", memberId));
  }

  @Override
  public Order findById(Long orderId) {
    return Optional
            .ofNullable(orderOutPort.findById(orderId))
            .orElseThrow(()-> new GlobalException("WNE-ORDER-0001", orderId));
  }

  @Override
  public List<Order> findAllById(Long aLong) {
    return Collections.emptyList();
  }

  // TODO
  private Member findMember(long memberId) {
    return Optional
            .ofNullable(memberShipOutPort.findById(memberId))
            .orElseThrow(() -> new GlobalException("WNE-ORDER-0002", memberId));
  }
}
