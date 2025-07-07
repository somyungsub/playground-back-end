package prototype.hexa.order.adapter.in.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import prototype.hexa.config.redis.RedisContainer;


@Component
@RequestMapping("/v1/orders/redis")
public class RedisPubSubAdapter {

  private final RedisContainer redisContainer;
  private final MessageListener messageListener2;
  private final MessageListener messageListener3;
  private final RedisTemplate<String, Object> redisTemplate;

  public RedisPubSubAdapter(RedisContainer redisContainer,
                            @Qualifier("redisSubscriber") MessageListener messageListener2,
                            @Qualifier("redisSubscriber2") MessageListener messageListener3,
                            RedisTemplate<String, Object> redisTemplate) {
    this.redisContainer = redisContainer;
    this.messageListener2 = messageListener2;
    this.messageListener3 = messageListener3;
    this.redisTemplate = redisTemplate;
  }

  @GetMapping
  public void publish(@RequestParam String message, @RequestParam String topic) {
    redisTemplate.convertAndSend(topic, message);  // 주제에 메시지 발행
  }

  @GetMapping("/add/{topic}")
  public void add(@PathVariable String topic) {
    redisContainer.addMessageListener(messageListener2, topic);
    redisContainer.addMessageListener(messageListener3, topic);
  }

}
