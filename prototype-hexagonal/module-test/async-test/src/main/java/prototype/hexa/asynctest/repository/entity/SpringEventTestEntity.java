package prototype.hexa.asynctest.repository.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prototype.hexa.asynctest.service.springevent.AsyncTestEvent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SPRING_EVENT_TEST")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SpringEventTestEntity {
  @Id
  @GeneratedValue
  private long id;
  private String name;
  private int age;
  private String type;

  public static SpringEventTestEntity of(AsyncTestEvent event, String type) {
    return new SpringEventTestEntity(0, event.getName(), event.getAge(), type);
  }
}
