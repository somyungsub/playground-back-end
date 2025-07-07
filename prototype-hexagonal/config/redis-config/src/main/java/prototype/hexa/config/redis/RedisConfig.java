package prototype.hexa.config.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import prototype.hexa.config.redis.properties.RedisProperties;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

  private final RedisProperties redisProperties;

  @PostConstruct
  public void postConstruct() {
    log.info("redisProperties host : {} ", redisProperties.getHost());
    log.info("redisProperties port : {} ", redisProperties.getPort());
    log.info("redisProperties database : {} ", redisProperties.getDatabase());
  }

  @Bean
  public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
//     비동기 작업을 처리할 Executor 설정
//    container.setTaskExecutor(Executors.newFixedThreadPool(4));  // 스레드 풀 추가
    return container;
  }

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory();
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory);

    // Custom ObjectMapper 설정
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.deactivateDefaultTyping(); // @class 정보 비활성화
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY); // @class 제외 설정

    // JSON 직렬화 설정
    GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);

    // 직렬화 설정
    redisTemplate.setKeySerializer(new StringRedisSerializer());
//    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    redisTemplate.setValueSerializer(serializer);

    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//    redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());  // JSON 직렬화
    redisTemplate.setHashValueSerializer(serializer);  // JSON 직렬화

    return redisTemplate;
  }

  @Bean
  public StreamMessageListenerContainer<String, MapRecord<String, Object, Object>> streamMessageListenerContainer(
    RedisConnectionFactory redisConnectionFactory) {

    // 메시지를 수신할 때 사용할 컨테이너 옵션 설정

    StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, Object, Object>> options =
      StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
        .pollTimeout(Duration.ofSeconds(1))  // 메시지 대기 시간 설정
        .keySerializer(new StringRedisSerializer())  // 키 직렬화 방식 설정
        .hashKeySerializer(new StringRedisSerializer())  // 해시 키 직렬화 설정
        .hashValueSerializer(new GenericJackson2JsonRedisSerializer())  // 값 직렬화 방식 설정 (Object -> JSON 직렬화)
        .build();


    // Redis Streams 메시지 리스너 컨테이너 생성
    return StreamMessageListenerContainer.create(redisConnectionFactory, options);
  }
}
