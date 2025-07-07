package prototype.hexa.consumertest.controller.redis;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class RedisStreamListener implements StreamListener<String, MapRecord<String, Object, Object>>  {
  @Override
  public void onMessage(MapRecord<String, Object, Object> message) {
    Object messageData = message.getValue().get("message");
    System.out.println("Received message from stream: " + messageData);

//    // RedisPublisherData 타입으로 역직렬화하여 사용할 수 있음
//    if (messageData instanceof RedisPublisherData) {
//      RedisPublisherData data = (RedisPublisherData) messageData;
//      System.out.println("Name: " + data.getName() + ", Age: " + data.getAge() + ", Message: " + data.getMessage());
//    }
  }
}
