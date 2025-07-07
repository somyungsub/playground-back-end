package prototype.hexa.asynctest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prototype.hexa.asynctest.service.springevent.AsyncTestEvent;
import prototype.hexa.asynctest.service.springevent.SpringEventTestService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/module-test/async")
class AsyncTestController {
  private final SpringEventTestService springEventTestService;
  @GetMapping
  void testSpringEvent(@RequestParam String name, int age) {
    AsyncTestEvent event = AsyncTestEvent.of("test-event", name, age);
    springEventTestService.publishEvent(event);
  }
}
