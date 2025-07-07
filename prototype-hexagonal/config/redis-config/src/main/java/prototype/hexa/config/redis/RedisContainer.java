package prototype.hexa.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class RedisContainer {
  private final RedisTemplate<String, Object> redisTemplate;
  private final RedisMessageListenerContainer container;
  private final StreamMessageListenerContainer<String, MapRecord<String, Object, Object>> streamMessageListenerContainer;

  @PostConstruct
  void init() {
//    redisTemplate.opsForStream().createGroup("test-stream", "redis-consumer-group");
    try {
      // "test-stream" 스트림에 "redis-consumer-group" 소비자 그룹을 생성
      redisTemplate.opsForStream().createGroup("test-stream", "redis-consumer-group");
      System.out.println("Consumer group created for stream 'test-stream'.");
    } catch (RedisSystemException e) {
      // 소비자 그룹이 이미 존재할 경우 예외가 발생하므로 예외를 처리
      if (e.getCause() instanceof io.lettuce.core.RedisCommandExecutionException
              && e.getCause().getMessage().contains("BUSYGROUP")) {
        System.out.println("Consumer group 'redis-consumer-group' already exists.");
      } else {
        throw e;  // 다른 예외는 다시 던짐
      }
    }
  }

  public void addMessageListener(MessageListener messageListener, String topic) {
    System.out.println("Adding listener for topic: " + topic);
    container.addMessageListener(messageListener, new ChannelTopic(topic));
  }

  public void removeMessageListener(MessageListener messageListener, String topic) {
    container.removeMessageListener(messageListener, new ChannelTopic(topic));
  }

  // Redis Streams 리스너 등록
  public void addStreamListener(StreamListener<String, MapRecord<String, Object, Object>> streamListener, String streamName) {
    System.out.println("Adding StreamListener for stream: " + streamName);
    // Redis Streams에 리스너 등록
//    streamMessageListenerContainer.receive(
//            Consumer.from("redis-consumer-group", "redis-consumer-1"),  // 소비자 그룹 및 소비자 ID 설정
//            StreamOffset.latest(streamName),
//            streamListener
//    );

    // Redis Streams에 리스너 등록 (새 메시지부터 읽기)
    streamMessageListenerContainer.receive(
            Consumer.from("redis-consumer-group", "consumer1"),  // 소비자 그룹 및 소비자 ID 설정
            StreamOffset.create(streamName, ReadOffset.from(">")),  // 최신 메시지부터 읽음
            streamListener
    );
    streamMessageListenerContainer.start();
  }
//  public void addMessageListener(RedisSubscriber subscriber, String topic) {
//    container.addMessageListener(createListenerAdapter(subscriber), new PatternTopic(topicName));
//  }

//  public MessageListenerAdapter createListenerAdapter(RedisSubscriber redisSubscriber) {
//    return new MessageListenerAdapter(redisSubscriber, "onMessage");
//  }

}
