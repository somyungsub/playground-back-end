package prototype.hexa.asynctest.service.springevent;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AsyncTestEvent extends ApplicationEvent {
  String name;
  int age;

  private AsyncTestEvent(Object source, String name, int age) {
    super(source);
    this.name = name;
    this.age = age;
  }

  public static AsyncTestEvent of(Object source, String name, int age) {
    return new AsyncTestEvent(source, name, age);
  }
}
