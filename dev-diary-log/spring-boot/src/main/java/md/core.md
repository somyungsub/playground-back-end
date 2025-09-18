# 코어 관련

## Fallback 애노테이션
- @Fallback 
  - spring 6.2부터 
  - 구현된 객체가 아무것도 없을때, 마지막으로 선택될 컴포넌트?

```java
import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Component;

@Component
@Fallback
class DummyImpl implements TestInterface {
  //
}

interface TestInterface {
  void test();
}
```