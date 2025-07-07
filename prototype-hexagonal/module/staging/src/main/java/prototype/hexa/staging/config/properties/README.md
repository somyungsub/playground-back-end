## 업무 Properties 설정
- resources/업무명.yml파일 생성후 key-value로 프로퍼티를 관리
- VO 객체
- @ConfigurationProperties, @ConstructorBinding 로 프로퍼티 매핑

```yml
# 예 sample.yml
sample:
  path: /v1/samples
  request-paths:
    - /v1/samples1
    - /v1/samples2
```

```java
// class 생성하여 sample.yml에서 정의한 값 매핑
@Value
@ConstructorBinding
@ConfigurationProperties(prefix = "sample")
public class SampleProperties {
    String path;
    List<String> requestPaths;
}
```

```text
@ConfigurationPropertiesScan 를 Applicatoin.java 에서 설정하여
bean 스캔을 하고 있습니다. 
1. SampleConfig 를 만들어서 활성화시켜서 사용해도 되고,
2. 활성화시키 않더라도 Applicatoin 이 실행될때, bean 등록을 하므로, 어디에서든 사용 가능
```

