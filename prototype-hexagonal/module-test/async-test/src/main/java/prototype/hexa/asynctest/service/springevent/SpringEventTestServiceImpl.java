package prototype.hexa.asynctest.service.springevent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpringEventTestServiceImpl implements SpringEventTestService {
  public final ApplicationEventPublisher applicationEventPublisher;

  @Override
  @Transactional
  public void publishEvent(AsyncTestEvent event) {
    log.info("publishEvent thread: {}", Thread.currentThread().getName());
    applicationEventPublisher.publishEvent(event);
    if (true) {
      throw new RuntimeException("서비스실패");
    }
  }
}
