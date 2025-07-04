# 테스트 환경 구축하기

## Stub, Mock
- 스텁 대역 : 테스트를 위한 객체 정도? 
- 목 대역 : 스텁 + 행위 인터렉션 등 더 필요한 경우? 스텁이랑 나눌 필요는 없음

## 환경구성
- @Spring BootTest
- @TestConfiguration
- @Import
- h2 디비 등
- junit-platform.properties : junit 설정과 관련된 것들
- Mockito(부트 기본 탑재)
- 대역 한번씩 만들어 보기
- record 활용도 가능
- @TestConstructor 활용 -> 스프링이 생성자에 주입시켜줌. 레코드 활용시 autowiredMode 속성 설정 필요
- @Transactional -> 기본 롤백

```java
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@Import(TestConfiguration.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public record TestRegisterTest(TestRegister register) {
  //

  @Configuration
  static class TestConfiguration {
    // 설정 ~~
  }

}
```