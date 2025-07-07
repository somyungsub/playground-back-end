package prototype.hexa.order.adapter.in.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component(value = "redisSubscriber2")
public class RedisSubscriber2 implements MessageListener {
  @Override
  public void onMessage(Message message, byte[] pattern) {
    String channel = new String(pattern, StandardCharsets.UTF_8);
    System.out.println("onMessage channel3 : " + channel);
    System.out.println("onMessage message3 : " + message);
  }
}
