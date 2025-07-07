package prototype.hexa.consumertest.controller.redis;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.web.bind.annotation.*;
import prototype.hexa.common.api.ApiResponse;
import prototype.hexa.config.redis.RedisContainer;

import javax.annotation.PostConstruct;
import java.util.List;


@RestController
//@Component
@RequestMapping("/v1/consume-test/redis")
public class RedisSubAdapter {

  private final RedisContainer redisContainer;
  private final RedisStreamListener redisStreamListener;
  private final MessageListener redisSubscriber;
  private final MessageListener redisSubscriber2;

  @PostConstruct
  public void init() {
    redisContainer.addStreamListener(redisStreamListener, "test-stream");
  }

  public RedisSubAdapter(
    @Qualifier("redisSubscriber") MessageListener redisSubscriber,
    @Qualifier("redisSubscriber2") MessageListener redisSubscriber2,
    RedisStreamListener redisStreamListener,
    RedisContainer redisContainer
  ) {
    this.redisContainer = redisContainer;
    this.redisStreamListener = redisStreamListener;
    this.redisSubscriber = redisSubscriber;
    this.redisSubscriber2 = redisSubscriber2;
  }


  @GetMapping("/add/{topic}")
  public ApiResponse<String> add(@PathVariable String topic) {
    redisContainer.addMessageListener(redisSubscriber, topic);
    redisContainer.addMessageListener(redisSubscriber2, topic);
    return ApiResponse.ok("OK");
  }

  @GetMapping("/add-topics")
  public ApiResponse<String> addTopics(@RequestParam List<String> topics) {
    if (CollectionUtils.isNotEmpty(topics)) {
      topics.forEach(topic -> redisContainer.addMessageListener(redisSubscriber, topic));
    }
    return ApiResponse.ok("OK");
  }


  @GetMapping("/add/streams/{streamName}")
  public ApiResponse<String> addStream(@PathVariable String streamName) {
    redisContainer.addStreamListener(redisStreamListener, streamName);
    return ApiResponse.ok("OK");
  }
}
