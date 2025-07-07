package prototype.hexa.consumertest.controller.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMqSubscriber {

  // default
//  @RabbitListener(queues = "${prototype.hexa.producer-test.queue-test-name}")
//  public void receiveMessageFromQueue2(String message) {
//    log.info("Received message from queue2: {}", message);
//  }

  @RabbitListener(queues = "#{producerTestConfig.producerTestProperties.queueTestName}")
  public void receiveMessageFromQueue(String message) {
    log.info("Received -> from queueTestName Thread : {}", Thread.currentThread().getName());
    log.info("Received -> from queueTestName: {}", message);
  }

  @RabbitListener(queues = "testQueue")
  public void receiveMessageFromTest(String message) {
    log.info("Received -> from testQueue Thread : {}", Thread.currentThread().getName());
    log.info("Received -> from testQueue: {}", message);
  }

}
