# JPA 관련 기술

## NaturalId 
- 우아한 형제들 : https://techblog.woowahan.com/17221

```java
//...

import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@NaturalIdCache
public class TestDomain {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "AGE")
  private int age;

  @NaturalId
  @Embedded
  private Email email;
  
  @Enumerated(EnumType.STRING)
  private Status status;
}

@Embeddable
class Email {
  private String host;
  private String domain;
}

enum Status {
  ACTIVE,
  DEACTIVE,
  PENDING
}

//...
```