package prototype.hexa.producertest.controller.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prototype.hexa.common.api.ApiResponse;
import prototype.hexa.config.rabbitmq.RabbitMqManager;
import prototype.hexa.config.rabbitmq.properties.RabbitMqProperties;
import prototype.hexa.producertest.config.properties.ProducerTestProperties;

@RestController
@RequestMapping("/v1/producer-test/rabbit-mq")
@RequiredArgsConstructor
class RabbitMqPublisher {
  private final RabbitMqManager rabbitMqManager;
  private final RabbitTemplate rabbitTemplate;
  private final RabbitMqProperties rabbitMqProperties;
  private final ProducerTestProperties producerTestProperties;

  @GetMapping("/publish")
  public ApiResponse<String> publish(@RequestParam String message) {
    rabbitTemplate.convertAndSend(rabbitMqProperties.getExchangeName(), "", message);
    return ApiResponse.ok("OK");
  }

  @GetMapping("/publish/fanout")
  public ApiResponse<String> publish(@RequestParam String fanoutName, @RequestParam String message) {
    rabbitTemplate.convertAndSend(fanoutName, "", message);
    return ApiResponse.ok("OK");
  }

  @GetMapping("/publish2")
  public ApiResponse<String> publish2(@RequestParam String message) {
    RabbitPublisherData data = RabbitPublisherData.builder()
            .name("test")
            .message(message)
            .build();
    rabbitTemplate.convertAndSend(rabbitMqProperties.getExchangeName(), "", data);
    return ApiResponse.ok("OK");
  }

  @GetMapping("/default")
  public ApiResponse<Binding> createDefault() {
    Binding binding = rabbitMqManager.bindingQueue(producerTestProperties.getQueueName());
    return ApiResponse.ok(binding);
  }

  @GetMapping("/queue")
  public ApiResponse<Binding> createQueue(@RequestParam String queueName) {
    Binding binding = rabbitMqManager.bindingQueue(queueName);
    return ApiResponse.ok(binding);
  }

  @GetMapping("/fanout")
  public ApiResponse<FanoutExchange> createFanout(@RequestParam String exchangeName, @RequestParam String queueName) {
    FanoutExchange fanout = rabbitMqManager.createFanout(exchangeName, queueName);
    return ApiResponse.ok(fanout);
  }
}
