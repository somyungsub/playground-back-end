package prototype.hexa.order.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prototype.hexa.common.annotation.WebAdapter;
import prototype.hexa.common.api.ApiResponse;
import prototype.hexa.order.application.port.in.OrderUseCase;
import prototype.hexa.order.application.port.in.command.ProductOrderCommand;
import prototype.hexa.order.domain.Order;

@WebAdapter
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderWebAdapter {
  private final OrderUseCase orderUseCase;
  private final OrderWebMapper orderWebMapper;

  @PostMapping
  public ApiResponse<OrderResponse> order(@RequestBody OrderRequest request) {
    ProductOrderCommand orderCommand = orderWebMapper.toCommand(request);
    Order order = orderUseCase.orderProduct(orderCommand);
    return ApiResponse.ok(orderWebMapper.toResponse(order));
  }

  @GetMapping("/{id}")
  public ApiResponse<OrderResponse> get(@PathVariable long id) {
    Order order = orderUseCase.findById(id);
    return ApiResponse.ok(orderWebMapper.toResponse(order));
  }
  @GetMapping("/{orderId}/members/{memberId}")
  public ApiResponse<OrderResponse> get(@PathVariable long orderId, @PathVariable long memberId) {
    Order order = orderUseCase.findByIdWithMember(orderId, memberId);
    return ApiResponse.ok(orderWebMapper.toResponse(order));
  }
}
