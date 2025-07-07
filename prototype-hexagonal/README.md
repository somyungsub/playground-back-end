# 실행 참고

## 실행환경 주요 소프트웨어 버전 참고 
- JDK : 17
- Spring Boot : 2.7.3
- Gradle: 8.10.2
- Kotlin : 1.9.10
- Nodejs : 16.19.1

## 도커참고
- ./docker 디렉토리
- 환경 대부분 계정아이디 비번은 (docker-compose 파일 환경정보 참고)
  - id: root
  - password: password

## nodejs 실행 
- bootstrap/node-server-app
  - 간단한 웹서버 실행구조 (express)
- 라이브러리 관련정보는 package.json 참고

```shell
## nvm or nodejs 설치 필요,

# 디렉토리 이동
$ cd bootstrap/node-server-app
# 라이브러리 install 
$ npm i 
## 실행
$ npm run start 
```

## SpringBoot 웹 애플리케이션 실행관련
- bootstrap : 실행환경영역, 모음
  - 각 모듈 참고
  - application.yml 정보 확인
  - 주요 테스트 모듈
    - sample-app
    - web-test-app
    - consumer-app
    - producer-app
- config 모듈 : 기술설정
- module 모듈 : 실행소스
- module-test 모듈 : 간단한 테스트용
- 관련 api 는 postman 산출물 참고

### 실행 active
- profile : local 필수
- 설정 관련 정보는 각 모듈 참고
```yaml
## sample-app -> application.yml
server:
  port: 29999
spring:
  profiles:
    group:
      local: ["local"]
  config:
    import:
      - application-core.yml
      - application-rdb.yml
      - application-r2dbc.yml
      - application-redis.yml
      - application-nosql.yml
      - application-kafka.yml
      - application-swagger.yml
      - application-sample.yml
      - application-member.yml
      - application-order.yml
  messages:
    basename: message_common, message_sample, message_sample_spread, message_member, message_order
    encoding: UTF-8
```
