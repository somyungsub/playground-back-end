package prototype.hexa.config.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMqManager {
  private final ConfigurableListableBeanFactory beanFactory;
  private final FanoutExchange fanoutExchange;
  private final RabbitAdmin rabbitAdmin;

//  public RabbitMqManager(FanoutExchange fanoutExchange, ConnectionFactory connectionFactory) {
//    log.info("RabbitMqManager init... {}", connectionFactory);
//    this.fanoutExchange = fanoutExchange;
//    this.rabbitAdmin = new RabbitAdmin(connectionFactory);
//  }

  public FanoutExchange createFanout(String fanoutExchangeName) {
    FanoutExchange fanoutExchange = new FanoutExchange(fanoutExchangeName);
    rabbitAdmin.declareExchange(fanoutExchange);
//    log.info("Fanout Exchange created: {}", fanoutExchangeName);
    registerBean(fanoutExchangeName, fanoutExchange);
    return fanoutExchange;
  }

  public Binding bindingQueue(String queueName) {
    Queue queue = createQueue(queueName);
    return createBinding(fanoutExchange, queue);
  }

  public FanoutExchange createFanout(String fanoutExchangeName, String queueName) {
    Queue queue = createQueue(queueName);
    FanoutExchange fanout = createFanout(fanoutExchangeName);
    Binding binding = createBinding(fanout, queue);
    return fanout;
  }

  private Queue createQueue(String queueName) {
    Queue queue = new Queue(queueName, true);
    registerBean(queueName, queue);
    return queue;
  }

  private Binding createBinding(FanoutExchange fanoutExchange, Queue queue) {
    Binding binding = BindingBuilder.bind(queue).to(fanoutExchange);
    rabbitAdmin.declareBinding(binding);
    String join = StringUtils.join(fanoutExchange.getName(), "-", queue.getName());
    registerBean(join, binding);
    return binding;
  }

  // 동적으로 생성한 빈을 IoC 컨테이너에 등록하는 메서드
  private void registerBean(String beanName, Object bean) {
    if (!beanFactory.containsBean(beanName)) {
      beanFactory.registerSingleton(beanName, bean);  // IoC 컨테이너에 빈으로 등록
    }
  }
}
