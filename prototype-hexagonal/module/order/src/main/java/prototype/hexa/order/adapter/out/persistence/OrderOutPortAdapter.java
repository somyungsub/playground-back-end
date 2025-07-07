package prototype.hexa.order.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import prototype.hexa.common.annotation.PersistenceAdapter;
import prototype.hexa.order.application.port.out.OrderOutPort;
import prototype.hexa.order.domain.Order;

@PersistenceAdapter
@RequiredArgsConstructor
public class OrderOutPortAdapter implements OrderOutPort {
  private final OrderRepository orderRepository;
  private final OrderOutPortMapper orderOutPortMapper;

  @Override
  public Order order(Order order, long memberId) {
    OrderJpaEntity orderJpaEntity = orderOutPortMapper.toEntity(order, memberId);
    OrderJpaEntity save = orderRepository.save(orderJpaEntity);
    return orderOutPortMapper.toDomain(save);
  }

  @Override
  public Order findById(Long orderId) {
    return orderRepository
            .findById(orderId)
            .map(orderOutPortMapper::toDomain)
            .orElse(null);
  }
}
