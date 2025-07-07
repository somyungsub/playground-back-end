package prototype.hexa.consumertest.controller.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component(value = "redisSubscriber")
public class RedisSubscriber implements MessageListener {
  @Override
  public void onMessage(Message message, byte[] pattern) {
    String channel = new String(pattern, StandardCharsets.UTF_8);
    System.out.println("consume-test-onMessage channel2 : " + channel);
    System.out.println("consume-test-onMessage message2 : " + message);
  }
}
