# Playground Backend - 헥사고날 아키텍처 프로젝트 분석

## 📋 프로젝트 개요

**프로젝트명**: Playground Backend - Hexagonal Architecture Prototype  
**목적**: 헥사고날 아키텍처의 실무 적용 가능한 완전한 구현 예제 제공  
**아키텍처**: Hexagonal Architecture (Ports & Adapters Pattern)  
**빌드 도구**: Gradle Multi-Module  
**언어**: Java 17, Kotlin (일부), Node.js (node-server-app)

---

## 🏗️ 아키텍처 패턴

### Hexagonal Architecture (포트와 어댑터)

```
┌─────────────────────────────────────────────────────────────────┐
│                        ADAPTERS (외부)                           │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │ Web Adapter  │  │Redis Adapter │  │Event Adapter │          │
│  │  (HTTP API)  │  │  (Pub/Sub)   │  │(Kafka/Rabbit)│          │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘          │
│         │                 │                 │                   │
│         └────────────────┬┴─────────────────┘                   │
│                          ▼                                      │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │                    PORTS (Inbound)                       │   │
│  │              UseCase 인터페이스 (port/in/)               │   │
│  └─────────────────────────┬───────────────────────────────┘   │
│                            ▼                                    │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │               APPLICATION LAYER                          │   │
│  │                Service (@UseCase)                        │   │
│  │            Command / Query 객체                          │   │
│  └─────────────────────────┬───────────────────────────────┘   │
│                            ▼                                    │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │                   DOMAIN LAYER                           │   │
│  │          Entity, Value Object, Enumeration               │   │
│  │       (Order, Member, Payment, Email, OrderStatus)       │   │
│  └─────────────────────────┬───────────────────────────────┘   │
│                            ▼                                    │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │                   PORTS (Outbound)                       │   │
│  │              Repository 인터페이스 (port/out/)            │   │
│  └─────────────────────────┬───────────────────────────────┘   │
│                            ▼                                    │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │  JPA Adapter │  │R2DBC Adapter │  │Mongo Adapter │          │
│  │   (MySQL)    │  │ (PostgreSQL) │  │  (MongoDB)   │          │
│  └──────────────┘  └──────────────┘  └──────────────┘          │
└─────────────────────────────────────────────────────────────────┘
```

---

## 📁 프로젝트 구조

### 최상위 구조

```
playground-back-end/
├── prototype-hexagonal/     # 메인 헥사고날 프로젝트
├── common-dependencies/     # 공통 의존성 관리
├── dev-diary-log/           # 개발 일기 및 학습 자료
├── docker/                  # Docker 설정 파일
├── build.gradle             # 루트 빌드 설정
├── settings.gradle          # 모듈 포함 설정
└── gradle.properties        # Gradle 프로퍼티
```

### prototype-hexagonal 상세 구조

```
prototype-hexagonal/
├── bootstrap/               # 실행 가능한 애플리케이션 (8개)
│   ├── sample-app/          # 메인 통합 앱 (포트 29999)
│   ├── consumer-app/        # 메시지 컨슈머
│   ├── producer-app/        # 메시지 프로듀서
│   ├── designer-app/        # 디자이너 앱
│   ├── router-db-app/       # DB 라우팅 테스트
│   ├── runtime-app/         # 런타임 테스트
│   ├── web-test-app/        # 웹 테스트
│   └── node-server-app/     # Node.js 서버 (Express)
│
├── module/                  # 도메인 모듈
│   ├── common/              # 공통 기능 (어노테이션, 예외, API 응답)
│   ├── membership/          # 회원 도메인
│   ├── order/               # 주문 도메인
│   ├── sample/              # 샘플 도메인 (다양한 DB 연동)
│   ├── auth/                # 인증 도메인 (준비 중)
│   ├── engine/              # 엔진 도메인 (준비 중)
│   ├── management/          # 관리 도메인 (준비 중)
│   ├── staging/             # 스테이징 도메인 (준비 중)
│   └── router-db/           # DB 라우팅 모듈
│
├── config/                  # 설정 모듈 (플러그인 방식)
│   ├── core-config/         # 핵심 설정
│   ├── rdb-config/          # JPA/Hibernate 설정
│   ├── r2dbc-config/        # R2DBC 리액티브 DB
│   ├── nosql-config/        # MongoDB 설정
│   ├── redis-config/        # Redis 설정
│   ├── kafka-config/        # Kafka 설정
│   ├── rabbit-mq-config/    # RabbitMQ 설정
│   ├── jooq-config/         # JOOQ 설정
│   ├── router-config/       # DB 라우팅 설정
│   ├── swagger-config/      # Swagger API 문서
│   └── third-party-config/  # QueryDSL 등
│
├── module-test/             # 테스트 모듈
│   ├── consumer-test/       # 컨슈머 테스트
│   ├── producer-test/       # 프로듀서 테스트
│   └── jdk-test/            # JDK 기능 테스트
│
└── docker/                  # Docker Compose 파일
    ├── docker-compose-db.yml
    ├── docker-compose-kafka.yml
    ├── docker-compose-rabbitmq.yml
    └── docker-compose-mongo-rs.yml
```

---

## 🎯 핵심 도메인 모듈

### 1. Common Module (공통)

**위치**: `module/common/`

**주요 컴포넌트**:
- **커스텀 어노테이션**:
  - `@UseCase`: 비즈니스 로직 서비스 마킹 (Spring `@Service` 포함)
  - `@WebAdapter`: HTTP 엔드포인트 마킹 (Spring `@RestController` 포함)
  - `@PersistenceAdapter`: DB 접근 어댑터 마킹 (Spring `@Repository` 포함)
  - `@EventConsumer`: 이벤트 컨슈머 마킹
  - `@EventProducer`: 이벤트 프로듀서 마킹

- **API 응답 포맷**:
  - `ApiResponse<T>`: 표준 API 응답 래퍼
  - `ApiError`: 에러 응답 구조
  - `ApiHeader`: 응답 헤더 정보

- **포트 인터페이스**:
  - `CommandUseCase<Domain, Command, ID>`: 명령 유스케이스
  - `QueryUseCase<Domain, Query, ID>`: 조회 유스케이스

- **예외 처리**:
  - `GlobalException`: 전역 예외
  - `GlobalExceptionHandler`: 전역 예외 핸들러

- **검증**:
  - `SelfValidating`: 자체 검증 추상 클래스
  - `SelfBuilderValidating`: 빌더 패턴 검증

### 2. Membership Module (회원)

**위치**: `module/membership/`

**도메인 모델**:
- `Member`: 회원 추상 클래스
- `AdultMember`: 성인 회원
- `KidsMember`: 어린이 회원
- `Email`: 이메일 Value Object (검증 포함)
- `PhoneNumber`: 전화번호 Value Object
- `Address`: 주소 Value Object
- `RegistrationType`: 가입 유형 Enum

**주요 기능**:
- 회원 가입 (`JoinMemberShip` Command)
- 회원 조회 (`QueryMemberShip` Query)
- 도메인 서비스: `MemberFactory`, `MemberManager`

**API 엔드포인트**:
- `POST /v1/membership/join`: 회원 가입
- `GET /v1/membership/{id}`: 회원 조회

### 3. Order Module (주문)

**위치**: `module/order/`

**도메인 모델**:
- `Order`: 주문 엔티티
- `Payment`: 결제 정보 Value Object
- `OrderStatus`: 주문 상태 Enum (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)

**주요 기능**:
- 상품 주문 (`ProductOrderCommand`)
- 주문 조회
- Redis Pub/Sub 연동 (주문 이벤트)

**API 엔드포인트**:
- `POST /v1/orders`: 주문 생성
- `GET /v1/orders/{id}`: 주문 조회

### 4. Sample Module (샘플)

**위치**: `module/sample/`

**목적**: 다양한 데이터베이스 연동 테스트

**지원 DB**:
- **JPA/Hibernate**: MySQL/H2
- **R2DBC**: PostgreSQL (리액티브)
- **MongoDB**: NoSQL 문서 DB
- **Node.js 연동**: Express 서버와 통신

**도메인 모델**:
- `Sample`: 샘플 엔티티
- `Spread`: 스프레드시트 형태 데이터
- `SampleNode`: Node.js 연동 데이터

**API 엔드포인트**:
- `POST /v1/sample/rdb`: JPA 저장
- `POST /v1/sample/r2dbc`: R2DBC 저장
- `POST /v1/sample/mongo`: MongoDB 저장
- `GET /v1/sample/node`: Node.js 서버 호출

---

## ⚙️ 설정 모듈 (Config)

### 플러그인 방식 설계

각 설정 모듈은 독립적으로 추가/제거 가능하며, 부트스트랩 앱의 `build.gradle`에 의존성을 추가하면 자동으로 활성화됩니다.

### 주요 설정 모듈

| 모듈 | 설명 | 주요 클래스 |
|------|------|------------|
| **core-config** | 핵심 설정 (Web, CORS) | `CoreConfig`, `WebConfig` |
| **rdb-config** | JPA/Hibernate 설정 | `RdbConfig`, `RdbConfigProperties` |
| **r2dbc-config** | 리액티브 DB 설정 | `R2dbcConfig` |
| **nosql-config** | MongoDB 설정 | `MongoConfig` |
| **redis-config** | Redis 설정 | `RedisConfig`, `RedisContainer` |
| **kafka-config** | Kafka 메시징 | `application-kafka.yml` |
| **rabbit-mq-config** | RabbitMQ 메시징 | `RabbitMQConfig`, `RabbitMqManager` |
| **jooq-config** | JOOQ SQL 빌더 | `JooqConfig` |
| **router-config** | 동적 DB 라우팅 | `RoutingDataSource`, `DataSourceContextHolder` |
| **swagger-config** | API 문서화 | `SwaggerConfig` |
| **third-party-config** | QueryDSL 등 | `QuerydslConfiguration` |

---

## 🚀 부트스트랩 애플리케이션

### 1. sample-app (메인 앱)

**포트**: 29999  
**설명**: 모든 도메인 모듈을 통합한 메인 애플리케이션  
**실행**: `./gradlew :prototype-hexagonal:bootstrap:sample-app:bootRun`

**포함 모듈**:
- membership, order, sample
- rdb-config, redis-config, nosql-config, r2dbc-config
- kafka-config, swagger-config

### 2. consumer-app (컨슈머)

**설명**: Kafka/RabbitMQ 메시지 컨슈머  
**주요 기능**:
- Kafka 토픽 구독
- RabbitMQ 큐 리스닝
- Redis Pub/Sub 구독

### 3. producer-app (프로듀서)

**설명**: 메시지 프로듀서  
**주요 기능**:
- Kafka 메시지 발행
- RabbitMQ 메시지 발행
- Redis Pub/Sub 발행

### 4. node-server-app (Node.js)

**포트**: 3000  
**설명**: Express.js 기반 Node.js 서버  
**주요 기능**:
- MongoDB 연동
- Redis 연동
- Java 프로세스 실행 테스트

**실행**: `cd bootstrap/node-server-app && npm start`

### 5. router-db-app (DB 라우팅)

**설명**: 동적 데이터베이스 라우팅 테스트  
**주요 기능**:
- 런타임에 DB 전환 (Oracle ↔ PostgreSQL)
- HTTP 헤더 기반 라우팅

### 6. designer-app

**설명**: 디자이너 전용 애플리케이션

### 7. runtime-app

**설명**: JDK 런타임 기능 테스트 (Virtual Threads 등)

### 8. web-test-app

**설명**: 웹 기능 테스트 애플리케이션

---

## 🔧 기술 스택

### 언어 & 런타임
- **Java**: 17 (메인 언어)
- **Kotlin**: 일부 모듈 (KotlinWebAdapter)
- **Node.js**: node-server-app

### 프레임워크
- **Spring Boot**: 2.7.3
- **Spring Data JPA**: ORM
- **Spring WebFlux**: 리액티브 웹
- **Spring Data R2DBC**: 리액티브 DB
- **Express.js**: Node.js 웹 프레임워크

### 데이터베이스
- **H2**: 개발/테스트 인메모리 DB
- **MySQL**: 프로덕션 RDB
- **PostgreSQL**: R2DBC 리액티브 DB
- **MongoDB**: NoSQL 문서 DB
- **Redis**: 캐시/Pub-Sub

### ORM & 쿼리
- **JPA/Hibernate**: ORM
- **R2DBC**: 리액티브 DB 접근
- **QueryDSL**: 타입 안전 쿼리
- **JOOQ**: SQL 쿼리 빌더

### 메시징
- **Apache Kafka**: 이벤트 스트리밍
- **RabbitMQ**: 메시지 큐
- **Redis Pub/Sub**: 실시간 이벤트

### 라이브러리
- **Lombok**: 1.18.34 (보일러플레이트 제거)
- **MapStruct**: 1.5.5 (객체 매핑)
- **Jackson**: 2.13.3 (JSON 직렬화)
- **Guava**: 31.1 (유틸리티)
- **Log4j**: 2.17.2 (로깅)
- **Netty**: 4.1.79 (네트워크)

### 테스트
- **JUnit 5 (Jupiter)**: 테스트 프레임워크
- **Mockito**: 5.18.0 (모킹)
- **JUnit Pioneer**: 2.3.0 (JUnit 확장)

### 빌드 & 배포
- **Gradle**: 멀티 모듈 빌드
- **Docker**: 컨테이너화
- **Docker Compose**: 로컬 개발 환경

---

## 📊 데이터 흐름

### Order 생성 흐름 예시

```
1. HTTP Request
   POST /v1/orders
   Body: { "productId": 1, "quantity": 2, "memberId": 100 }
   
2. Web Adapter
   OrderWebAdapter.order(OrderRequest)
   ↓ MapStruct 변환
   
3. UseCase (Inbound Port)
   OrderUseCase.orderProduct(ProductOrderCommand)
   
4. Service (@UseCase)
   OrderService.orderProduct()
   ↓ 도메인 생성 및 검증
   
5. Domain Layer
   Order.withoutId() 
   - 비즈니스 규칙 검증
   - 도메인 이벤트 생성
   
6. Outbound Port
   OrderOutPort.order(Order, memberId)
   
7. Persistence Adapter
   OrderOutPortAdapter
   ↓ Entity 변환
   OrderJpaEntity → JPA 저장
   
8. 회원 정보 조회
   MembershipOutPort.findById(memberId)
   
9. 도메인 조합
   Order.withMember(Member)
   
10. Response
    ApiResponse.ok(OrderResponse)
```

---

## 🎨 설계 패턴

### 1. Hexagonal Architecture
- 포트와 어댑터 패턴
- 도메인 중심 설계
- 외부 의존성 역전

### 2. Domain-Driven Design (DDD)
- 도메인 엔티티
- Value Object (Email, Payment, Address)
- Domain Service (MemberFactory, MemberManager)
- Enumeration (OrderStatus, RegistrationType)

### 3. Command/Query Separation (CQS)
- Command: 상태 변경 (save, update, delete)
- Query: 조회 (findById, findAll)

### 4. Mapper Pattern
- MapStruct를 사용한 객체 변환
- Request → Command → Domain → Entity
- Entity → Domain → Response

### 5. Factory Pattern
- `MemberFactory`: 회원 타입별 생성

### 6. Strategy Pattern
- 다양한 DB 어댑터 (JPA, R2DBC, MongoDB)

---

## 🔑 핵심 개념

### 1. 포트 (Ports)

**Inbound Port (들어오는 포트)**:
```java
// UseCase 인터페이스
public interface OrderUseCase {
    Order orderProduct(ProductOrderCommand command);
    Order findById(Long id);
}
```

**Outbound Port (나가는 포트)**:
```java
// Repository 인터페이스
public interface OrderOutPort {
    Order save(Order order);
    Optional<Order> findById(Long id);
}
```

### 2. 어댑터 (Adapters)

**Inbound Adapter (Web)**:
```java
@WebAdapter
@RequestMapping("/v1/orders")
public class OrderWebAdapter {
    private final OrderUseCase orderUseCase;
    
    @PostMapping
    public ApiResponse<OrderResponse> order(@RequestBody OrderRequest request) {
        // ...
    }
}
```

**Outbound Adapter (Persistence)**:
```java
@PersistenceAdapter
public class OrderOutPortAdapter implements OrderOutPort {
    private final OrderJpaRepository repository;
    
    @Override
    public Order save(Order order) {
        // Entity 변환 및 저장
    }
}
```

### 3. 도메인 모델

**엔티티**:
```java
public class Order {
    private Long id;
    private Long memberId;
    private Long productId;
    private int quantity;
    private Payment payment;
    private OrderStatus status;
    
    // 비즈니스 로직
    public void confirm() { ... }
    public void cancel() { ... }
}
```

**Value Object**:
```java
public class Email {
    private final String value;
    
    public Email(String value) {
        validate(value);
        this.value = value;
    }
    
    private void validate(String value) {
        // 이메일 형식 검증
    }
}
```

### 4. Command & Query

**Command (명령)**:
```java
@Getter
@Builder
public class ProductOrderCommand extends SelfValidating<ProductOrderCommand> {
    @NotNull
    private Long productId;
    
    @NotNull
    private Long memberId;
    
    @Min(1)
    private int quantity;
}
```

**Query (조회)**:
```java
@Getter
@Builder
public class QueryMemberShip {
    @NotNull
    private Long memberId;
}
```

---

## 🧪 테스트 구조

### module-test/

**consumer-test**:
- Kafka 컨슈머 테스트
- RabbitMQ 컨슈머 테스트
- Redis Subscriber 테스트

**producer-test**:
- Kafka 프로듀서 테스트
- RabbitMQ 프로듀서 테스트
- Redis Publisher 테스트

**jdk-test**:
- JDK 21 Virtual Threads 테스트
- 새로운 JDK 기능 실험

---

## 🐳 Docker 환경

### docker-compose-db.yml
```yaml
services:
  mysql:
    image: mysql:8.0
    ports:
      - "3306:3306"
  
  postgres:
    image: postgres:14
    ports:
      - "5432:5432"
  
  mongodb:
    image: mongo:5.0
    ports:
      - "27017:27017"
  
  redis:
    image: redis:7.0
    ports:
      - "6379:6379"
```

### docker-compose-kafka.yml
```yaml
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:latest
  
  kafka:
    image: confluentinc/cp-kafka:latest
    ports:
      - "9092:9092"
```

### docker-compose-rabbitmq.yml
```yaml
services:
  rabbitmq:
    image: rabbitmq:3-management
    ports:
      - "5672:5672"
      - "15672:15672"
```

---

## 📝 새 도메인 추가 가이드

### 1. 모듈 생성
```
module/new-domain/
├── domain/                  # 도메인 엔티티, VO, Enum
├── application/
│   ├── port/in/             # UseCase 인터페이스
│   ├── port/out/            # Repository 인터페이스
│   └── service/             # Service 구현 (@UseCase)
└── adapter/
    ├── in/web/              # WebAdapter (@WebAdapter)
    └── out/persistence/     # PersistenceAdapter
```

### 2. settings.gradle에 추가
```gradle
include 'prototype-hexagonal:module:new-domain'
```

### 3. 부트스트랩 앱에 의존성 추가
```gradle
dependencies {
    implementation project(':prototype-hexagonal:module:new-domain')
}
```

### 4. 도메인 구현
- Domain Entity 작성
- UseCase 인터페이스 정의
- Service 구현 (@UseCase)
- WebAdapter 구현 (@WebAdapter)
- PersistenceAdapter 구현 (@PersistenceAdapter)

---

## 🔍 주요 특징

### 1. 멀티 모듈 구조
- 37개의 독립적인 Gradle 모듈
- 관심사 분리 (Separation of Concerns)
- 플러그인 방식 설정 추가/제거

### 2. 다중 데이터 소스
- JPA, R2DBC, MongoDB, JOOQ 동시 지원
- 각 도메인별 최적 DB 선택 가능

### 3. 이벤트 주도 설계
- Kafka, RabbitMQ, Redis Pub/Sub
- 도메인 이벤트 발행/구독

### 4. 마이크로서비스 준비
- 각 부트스트랩 앱이 독립 배포 가능
- 도메인 모듈 간 느슨한 결합

### 5. 타입 안전성
- MapStruct 객체 매핑
- QueryDSL 타입 안전 쿼리
- Lombok 보일러플레이트 제거

### 6. API 문서화
- Swagger/OpenAPI 자동 생성
- 표준 API 응답 포맷

---

## 📚 학습 자료

### dev-diary-log/
- **library/**: JPA 학습 자료
- **spring-boot/**: Spring Boot 핵심 개념
  - config.md: 설정 관련
  - core.md: 핵심 기능
  - security.md: 보안
  - test.md: 테스트

---

## 🎯 프로젝트 목표

### 1. 아키텍처 학습
- 헥사고날 아키텍처 실무 적용 예제
- 포트와 어댑터 패턴 완전 구현

### 2. 기술 스택 실험
- 다양한 DB 연동 테스트
- 메시징 시스템 통합
- JDK 신기능 테스트

### 3. 확장 가능한 설계
- 새로운 도메인 쉽게 추가
- 설정 모듈 플러그인 방식
- 마이크로서비스 전환 준비

### 4. 코드 품질
- 클린 코드 원칙
- SOLID 원칙 준수
- 테스트 가능한 설계

---

## 🚦 실행 방법

### 1. 데이터베이스 실행
```bash
cd docker
docker-compose -f docker-compose-db.yml up -d
```

### 2. 메시징 시스템 실행 (선택)
```bash
docker-compose -f docker-compose-kafka.yml up -d
docker-compose -f docker-compose-rabbitmq.yml up -d
```

### 3. 메인 애플리케이션 실행
```bash
./gradlew :prototype-hexagonal:bootstrap:sample-app:bootRun
```

### 4. API 테스트
```bash
# 회원 가입
curl -X POST http://localhost:29999/v1/membership/join \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","name":"홍길동","age":30}'

# 주문 생성
curl -X POST http://localhost:29999/v1/orders \
  -H "Content-Type: application/json" \
  -d '{"productId":1,"memberId":1,"quantity":2}'
```

### 5. Swagger UI
```
http://localhost:29999/swagger-ui.html
```

---

## 📊 모듈 의존성 그래프

```
bootstrap/sample-app
├── module/membership
│   └── module/common
├── module/order
│   └── module/common
├── module/sample
│   └── module/common
├── config/core-config
├── config/rdb-config
├── config/redis-config
├── config/nosql-config
├── config/r2dbc-config
├── config/kafka-config
└── config/swagger-config
```

---

## 🔐 보안 고려사항

- 환경별 설정 분리 (application-{profile}.yml)
- 민감 정보 외부화 (환경 변수)
- API 인증/인가 준비 (auth 모듈)

---

## 🎓 학습 포인트

1. **헥사고날 아키텍처**: 포트와 어댑터 패턴의 실제 구현
2. **DDD**: 도메인 중심 설계, Value Object, Domain Service
3. **멀티 모듈**: Gradle 멀티 모듈 프로젝트 구성
4. **다중 DB**: JPA, R2DBC, MongoDB 동시 사용
5. **메시징**: Kafka, RabbitMQ, Redis Pub/Sub 통합
6. **리액티브**: Spring WebFlux, R2DBC
7. **API 설계**: RESTful API, 표준 응답 포맷
8. **테스트**: 단위 테스트, 통합 테스트

---

## 📌 참고사항

- **Java 버전**: 최소 Java 17 필요
- **Virtual Threads**: JDK 21+ 필요
- **포트**: sample-app은 29999 포트 사용
- **H2 Console**: http://localhost:29999/h2-console
- **메모리**: 최소 4GB RAM 권장

---

## 🔄 최근 변경사항

- security.md 추가
- 헥사고날 테스트 추가
- 모듈 뎁스 조정
- 코드 정리 작업

---

## 📞 문의 및 기여

이 프로젝트는 학습 및 프로토타입 목적으로 제작되었습니다.  
헥사고날 아키텍처를 학습하고자 하는 개발자들에게 참고 자료로 활용될 수 있습니다.

---

**작성일**: 2026-01-31  
**분석 도구**: Kilo Code (Claude Sonnet 4.5)  
**프로젝트 상태**: 안정 (Active Development)
