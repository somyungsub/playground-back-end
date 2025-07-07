package prototype.hexa.producertest.controller.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prototype.hexa.common.api.ApiResponse;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/v1/producer-test/redis")
@RequiredArgsConstructor
class RedisPubAdapter {
  private final RedisTemplate<String, Object> redisTemplate;

  @GetMapping
  public ApiResponse<RedisPublisherData> publish(@RequestParam String message, @RequestParam String topic) {
    RedisPublisherData data = RedisPublisherData
            .builder()
            .age(30)
            .message(message)
            .name("ssss")
            .build();

    redisTemplate.convertAndSend(topic, data);
    return ApiResponse.ok(data);
  }
  @GetMapping("/streams")
  public ApiResponse<MapRecord<String, Object, Object>> publishStream(@RequestParam String message, @RequestParam String streamName) {
    RedisPublisherData data = RedisPublisherData
            .builder()
            .age(10)
            .message(message)
            .name("abc")
            .build();

    Map<Object, Object> messageData = new HashMap<>();
    messageData.put("message", data);

    MapRecord<String, Object, Object> record = StreamRecords.newRecord()
      .in(streamName)  // 스트림 이름
      .ofMap(messageData)  // 데이터 (Map)
      .withId(RecordId.autoGenerate());  // RecordId 자동 생성

    redisTemplate.opsForStream().add(record);
    System.out.println("Message published to stream: " + message);

    return ApiResponse.ok(record);
  }
}
