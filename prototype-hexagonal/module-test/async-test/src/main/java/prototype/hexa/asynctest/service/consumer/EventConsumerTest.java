package prototype.hexa.asynctest.service.consumer;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import prototype.hexa.asynctest.repository.SpringEventTestRepository;
import prototype.hexa.asynctest.repository.entity.SpringEventTestEntity;
import prototype.hexa.asynctest.service.springevent.AsyncTestEvent;
import prototype.hexa.common.annotation.EventConsumer;

@EventConsumer
@Slf4j
@RequiredArgsConstructor
public class EventConsumerTest {
  private final SpringEventTestRepository repository;

  @EventListener
  public void sync(AsyncTestEvent event) {
    log.info("subscribe-1 thread : {}", Thread.currentThread().getName());
    log.info("subscribe-1 : {}", event);

    SpringEventTestEntity entity = SpringEventTestEntity.of(event, "sync");
    repository.save(entity);
//    if (true) {
//      throw new RuntimeException("sync");
//    }
  }

  @Async(value = "customTaskExecutor")
  @EventListener
  public void async(AsyncTestEvent event) {
    log.info("subscribe-2 thread : {}", Thread.currentThread().getName());
    log.info("subscribe-2 : {}", event);
    SpringEventTestEntity entity = SpringEventTestEntity.of(event, "async");
    repository.save(entity);
//    if (true) {
//      throw new RuntimeException("async");
//    }
  }

  @Async(value = "customTaskExecutor")
  @EventListener
  @Transactional
  public void asyncTran(AsyncTestEvent event) {
    log.info("subscribe-2 thread : {}", Thread.currentThread().getName());
    log.info("subscribe-2 : {}", event);
    SpringEventTestEntity entity = SpringEventTestEntity.of(event, "asyncTran");
    repository.save(entity);
//    if (true) {
//      throw new RuntimeException("async");
//    }
  }
}
