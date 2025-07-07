package prototype.hexa.config.rabbitmq;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prototype.hexa.config.rabbitmq.properties.RabbitMqProperties;

@AllArgsConstructor
@Configuration
@EnableConfigurationProperties(RabbitMqProperties.class)
public class RabbitMQConfig {
  private final RabbitMqProperties rabbitMqProperties;

  @Bean
  public Queue sampleQueue() {
    return new Queue(rabbitMqProperties.getSampleQueueName(), true);
  }

  @Bean
  public Queue spreadQueue() {
    return new Queue(rabbitMqProperties.getSpreadQueueName(), true);
  }
  @Bean
  public Queue testQueue() {
    return new Queue(rabbitMqProperties.getTestQueueName(), true);
  }

  @Bean
  public FanoutExchange fanoutExchange() {
    return new FanoutExchange(rabbitMqProperties.getExchangeName());
  }

  @Bean
  public Binding bindingSample(Queue sampleQueue, FanoutExchange fanoutExchange) {
    return BindingBuilder.bind(sampleQueue).to(fanoutExchange);
  }

  @Bean
  public Binding bindingSpread(Queue spreadQueue, FanoutExchange fanoutExchange) {
    return BindingBuilder.bind(spreadQueue).to(fanoutExchange);
  }
  @Bean
  public Binding bindingTest(Queue testQueue, FanoutExchange fanoutExchange) {
    return BindingBuilder.bind(testQueue).to(fanoutExchange);
  }

  @Bean
  public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
    return new RabbitAdmin(connectionFactory);
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    return rabbitTemplate;
  }

}
