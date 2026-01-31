# playground-back-end 프로젝트 분석

> **분석 일자**: 2026-01-31
> **분석 도구**: Claude Sonnet 4.5

---

## 📋 프로젝트 개요

| 항목 | 내용 |
|------|------|
| **프로젝트명** | playground-back-end |
| **타입** | Gradle Composite Build (멀티모듈) |
| **빌드 도구** | Gradle 8.10.2 |
| **주 언어** | Java 17, 21 (Kotlin 1.9.10) |
| **프레임워크** | Spring Boot 2.7.3, 3.2.10 |
| **아키텍처** | Hexagonal Architecture + AI Integration |
| **총 Java 파일** | ~280개 |
| **Git 브랜치** | main |

---

## 🏗️ 전체 구조

```
playground-back-end/
├── common-dependencies/        # 공통 의존성 중앙 관리
├── spring-ai/                  # AI/LLM 실험 모듈
├── prototype-hexagonal/        # 헥사고날 아키텍처 프로토타입
└── dev-diary-log/             # 개발 일지 기록
```

### Composite Build 구성

```gradle
// settings.gradle
rootProject.name = 'playground-back-end'
includeBuild("common-dependencies")
includeBuild("spring-ai")
includeBuild("prototype-hexagonal")
includeBuild("dev-diary-log")
```

**장점**:
- 각 모듈 독립적 빌드/배포 가능
- 버전 분리 관리 (Spring Boot 2 vs 3, Java 17 vs 21)
- 느슨한 결합

---

## 🔧 모듈 상세 분석

### 1️⃣ common-dependencies

**역할**: 공통 라이브러리 의존성 중앙 관리

**구조**:
```
common-dependencies/
├── spring-boot-2/           # Spring Boot 2.7.3 스타터
├── spring-boot-3/           # Spring Boot 3.2.10 스타터
└── spring-boot-3-data/      # Spring Boot 3 + 데이터 기술
```

**주요 의존성**:
- **JSON**: Jackson 2.17.2
- **유틸**: Apache Commons (Lang3, Collections4, Math3), Guava 31.1-jre
- **네이티브**: Netty resolver (macOS M1/M2 지원)
- **로깅**: Log4j API 2.17.2
- **코드 생성**: Lombok 1.18.34, MapStruct 1.5.5.Final

---

### 2️⃣ spring-ai

**역할**: AI/LLM 통합 실험 모듈

**포트**: 9090

**주요 기능**:

#### 1) 기본 Chat
- Ollama 또는 OpenAI 모델 선택 가능
- CLI 기반 대화형 인터페이스
- 메모리 기반 대화 히스토리 (최대 10개)

#### 2) RAG (Retrieval-Augmented Generation)
- Elasticsearch 벡터 스토어
- PDF 문서 임베딩 (fastcampus-springai.pdf)
- 유사 문서 검색 후 답변 생성
- 필터 표현식 지원

#### 3) Tool Calling
- 외부 API 호출 (날씨 조회 등)
- WeatherTool: wttr.in API 활용
- 감정 평가 (EmotionEvaluation)

#### 4) MCP (Model Context Protocol)
- MCP 서버: 도구/리소스 제공
- MCP 클라이언트: 서버와 통신

**패키지 구조**:
```
springai/
├── config/
│   ├── SimpleChatConfig.java      # 기본 채팅
│   ├── ToolChatConfig.java        # Tool Calling
│   ├── RagChatConfig.java         # RAG
│   └── WebConfig.java
├── controller/
│   ├── ChatController.java
│   ├── ToolController.java
│   ├── RAGController.java
│   └── AiController.java
├── service/
│   ├── impl/
│   ├── tool/
│   │   └── WeatherTool.java
│   └── data/
│       ├── EmotionEvaluation.java
│       └── Emotion.java
└── SpringAiApplication.java
```

**설정 (application.yml)**:
```yaml
server:
  port: 9090

spring:
  ai:
    model:
      chat: ollama                # ollama 또는 openai
      embedding: ollama
    ollama:
      chat:
        options:
          model: mistral          # 기본 모델
    vectorstore:
      elasticsearch:
        initialize-schema: true
        index-name: spring-ai-document-
        dimensions: 1024
        similarity: cosine
```

**API 엔드포인트**:
```
POST /tool/call              # Tool Calling API
POST /tool/stream            # 스트리밍 응답 (SSE)
POST /tool/emotion           # 감정 평가

POST /rag/call               # RAG 기반 응답
POST /rag/stream             # 스트리밍 응답 (SSE)
POST /rag/emotion            # 감정 평가

POST /chat                   # 기본 채팅
POST /ai                     # AI 기본 API
```

**주요 의존성**:
- Spring AI BOM 1.0.2
- Ollama, OpenAI 모델
- Elasticsearch 8.18.0
- Spring AI Tika (문서 처리)

---

### 3️⃣ prototype-hexagonal

**역할**: 헥사고날 아키텍처 프로토타입 및 실험

**포트**: 29999 (sample-app)

**구조**:
```
prototype-hexagonal/
├── bootstrap/              # 실행 가능한 애플리케이션들
├── module/                 # 비즈니스 도메인 모듈들
├── module-test/            # 테스트 전용 모듈
├── config/                 # 기술 설정 모듈
└── docker/                 # Docker Compose 설정
```

#### 3-1. Bootstrap (실행 애플리케이션)

| 애플리케이션 | 설명 |
|-------------|------|
| sample-app | 핵심 샘플 애플리케이션 (포트 29999) |
| web-test-app | 웹 기능 테스트 |
| consumer-app | 메시지 컨슈머 (Kafka/RabbitMQ) |
| producer-app | 메시지 프로듀서 (Kafka/RabbitMQ) |
| router-db-app | 다중 DB 라우팅 테스트 |
| designer-app | 디자이너 앱 |
| runtime-app | 런타임 앱 |
| node-server-app | Node.js Express 서버 |

#### 3-2. Module (도메인 모듈)

| 모듈 | Java 파일 | 설명 |
|------|----------|------|
| sample | 92 | 기본 샘플 (JPA/R2DBC, Kotlin) |
| membership | 22 | 회원 관리 |
| order | 19 | 주문 관리 (Redis 캐시) |
| auth | - | 인증/권한 |
| staging | - | 스테이징 데이터 |
| engine | - | 엔진 |
| management | - | 운영 관리 |
| router-db | - | 다중 DB 라우팅 |
| common | - | 공통 유틸 |

**헥사고날 아키텍처 구조** (각 모듈):
```
[domain]/
├── adapter/
│   ├── in/
│   │   └── web/               # REST 컨트롤러 (인바운드)
│   └── out/
│       └── persistence/       # DB 어댑터 (아웃바운드)
│           ├── [Domain]JpaEntity.java
│           ├── [Domain]Repository.java
│           └── [Domain]OutPortAdapter.java
├── domain/
│   ├── model/                 # 도메인 모델
│   ├── service/               # 도메인 서비스
│   ├── vo/                    # Value Object
│   └── enumeration/           # 열거형
└── [Domain]UseCase.java       # Use Case 인터페이스
```

**아키텍처 다이어그램**:
```
       Inbound Adapter
    ↙                  ↘
[REST API]        [Message Queue]
    ↓                  ↓
  ┌─────────────────────────┐
  │      USE CASE           │
  │   (비즈니스 로직)        │
  └─────────────────────────┘
    ↓                  ↓
[Persistence]      [Cache]
    ↘                  ↙
      Outbound Adapter
```

#### 3-3. 주요 엔티티

**SampleJpaEntity**:
```java
@Entity
@Table(name = "SAMPLE")
public class SampleJpaEntity {
    @Id @GeneratedValue
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "code")
    private String code;

    @OneToMany(mappedBy = "sampleJpaEntity",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<SampleInputEntity> inputEntities;
}
```

**MemberJpaEntity**:
```java
@Entity
@Table(name = "MEMBER")
public class MemberJpaEntity {
    @Id @GeneratedValue
    private long id;
    private String name;
    private String password;
    private int age;
    private String email;
    private String address;
    private String registrationType;
    private String phoneNumber1;
    private String phoneNumber2;
    private String ip;  // 기본값: "127.0.0.1"
}
```

**OrderJpaEntity**:
```java
@Entity
@Table(name = "ORDER")
public class OrderJpaEntity {
    @Id @GeneratedValue
    private Long id;
    private String orderNo;
    private LocalDateTime orderDate;

    @ManyToOne
    private MemberJpaEntity member;
}
```

#### 3-4. Config (기술 설정)

| 설정 모듈 | 기술 |
|----------|------|
| jooq-config | JOOQ (타입안전 SQL) |
| r2dbc-config | R2DBC (반응형 DB) |
| redis-config | Redis 캐시, Testcontainers |
| rabbit-mq-config | RabbitMQ 메시징 |
| router-config | 다중 데이터소스 라우팅 |
| kafka-config | Kafka 이벤트 스트리밍 |

**다중 데이터소스 라우팅** (router-config):
```java
// RoutingDataSource.java
// DataSourceContextHolder.java
// HibernateInspector.java
→ 런타임에 DB 선택 (PostgreSQL/Oracle 등)
```

#### 3-5. Module-Test (테스트 모듈)

**jdk-test**: JDK 9-21 신규 기능 테스트
```java
@Test
void jdk9_processHandle() { ... }

@Test
void jdk16_mapMulti() { ... }

@Test
void jdk21_virtual_thread() { ... }
```

**async-test**: 비동기, JOOQ, 이벤트 테스트
- JOOQ Repository
- JDBC Repository
- Spring Event 처리

#### 3-6. Docker (인프라)

```
docker/
├── docker-compose-db.yml              # PostgreSQL, MySQL, Oracle
├── docker-compose-kafka.yml           # Kafka (단일)
├── docker-compose-kafka-multi.yml     # Kafka (클러스터)
├── docker-compose-mongo-rs.yml        # MongoDB Replica Set
└── docker-compose-rabbitmq.yml        # RabbitMQ
```

---

### 4️⃣ dev-diary-log

**역할**: 개발 일지 기록 모듈

**구조**:
```
dev-diary-log/
├── library/            # 라이브러리
└── spring-boot/        # Spring Boot 통합
```

---

## 🎯 데이터 접근 전략

프로젝트는 다층 데이터 접근 방식을 채택:

| 기술 | 용도 | 특징 |
|------|------|------|
| **JPA** | 전통적 ORM | Hibernate 기반, 엔티티 관리 |
| **R2DBC** | 반응형 DB | Non-blocking, Reactor 기반 |
| **JOOQ** | 타입안전 SQL | 복잡한 쿼리, 컴파일 타임 검증 |
| **JDBC** | 저수준 접근 | 직접 SQL 실행 |
| **MongoDB** | NoSQL | Document 기반 저장 |
| **Redis** | 캐시/세션 | In-memory, 빠른 응답 |

---

## 🔌 주요 기술 스택

### 백엔드 프레임워크
- Spring Boot 2.7.3, 3.2.10
- Spring WebFlux (반응형 웹)
- Spring Data JPA
- Spring Data R2DBC
- Spring Data MongoDB
- Spring Data Redis

### 메시징
- Apache Kafka
- RabbitMQ (AMQP)

### AI/ML
- Spring AI 1.0.2
- Ollama (로컬 LLM)
- OpenAI API
- Elasticsearch (벡터 스토어)

### 빌드 & 코드 생성
- Gradle 8.10.2
- Lombok 1.18.34
- MapStruct 1.5.5.Final
- QueryDSL (JPA, APT)

### 테스트
- JUnit 5 (Jupiter)
- Mockito 5.18.0
- JUnit Pioneer 2.3.0
- TestContainers

### 기타
- Kotlin 1.9.10
- Node.js 16.19.1 (node-server-app)

---

## 📝 설정 파일

### sample-app application.yml

```yaml
server:
  port: 29999

spring:
  profiles:
    group:
      local: ["local", "message-local"]
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
    basename: message_common, message_sample
```

### gradle.properties

```properties
springBootVersion2=2.7.3
springManageVersion2=1.1.4

springBootVersion3=3.2.10
springManageVersion3=1.1.5
```

---

## 🧪 테스트 전략

### 테스트 구조
- **단위 테스트**: JUnit 5, Mockito
- **통합 테스트**: Spring Boot Test
- **컨테이너 테스트**: TestContainers (Redis, MongoDB, PostgreSQL)
- **JDK 기능 테스트**: jdk-test 모듈
- **비동기 테스트**: async-test 모듈

### 테스트 설정
```gradle
test {
    useJUnitPlatform()  // JUnit 5 플랫폼
}
```

---

## 🌐 API 엔드포인트 요약

### Spring AI (9090)
```
# Tool Calling
POST /tool/call
POST /tool/stream
POST /tool/emotion

# RAG
POST /rag/call
POST /rag/stream
POST /rag/emotion

# Chat
POST /chat
POST /ai
```

### Prototype-Hexagonal (29999)
```
# Sample
GET    /sample/{id}
POST   /sample
PUT    /sample/{id}
DELETE /sample/{id}

# Member
GET    /member/{id}
POST   /member
PUT    /member/{id}
DELETE /member/{id}

# Order
GET    /order/{id}
POST   /order
PUT    /order/{id}
DELETE /order/{id}
```

---

## 💡 프로젝트 주요 특징

### 1. Composite Build 아키텍처
- 4개의 독립적인 빌드 모듈
- 각 모듈 별도 버전 관리
- 느슨한 결합, 높은 응집도

### 2. 헥사고날 아키텍처
- 포트/어댑터 패턴
- 비즈니스 로직과 인프라 분리
- 테스트 용이성

### 3. 최신 AI 기술 통합
- LLM (Ollama, OpenAI)
- RAG (Retrieval-Augmented Generation)
- Tool Calling
- MCP (Model Context Protocol)

### 4. 다층 데이터 접근
- JPA, R2DBC, JOOQ, MongoDB, Redis
- 상황에 맞는 최적 기술 선택

### 5. 이벤트 기반 아키텍처
- Kafka, RabbitMQ 지원
- 비동기 메시지 처리

### 6. JDK 다양화
- Java 17, 21 병행 사용
- 최신 JDK 기능 실험 (Virtual Thread 등)

### 7. Docker 기반 인프라
- 로컬 개발 환경 완비
- 다양한 미들웨어 지원

---

## 📊 통계

| 항목 | 값 |
|------|-----|
| 총 Java 파일 | ~280개 |
| 모듈 수 | 4개 (Composite Build) |
| 도메인 모듈 | 9개 |
| Bootstrap 앱 | 8개 |
| Config 모듈 | 6개 |
| 지원 포트 | 9090 (AI), 29999 (Hexagonal) |
| Spring Boot 버전 | 2.7.3, 3.2.10 |
| Java 버전 | 17, 21 |

---

## 🎓 학습 포인트

이 프로젝트는 다음을 학습하기 위한 **Playground**입니다:

1. **헥사고날 아키텍처**: 포트/어댑터 패턴 실습
2. **Spring AI**: LLM 통합, RAG, Tool Calling
3. **멀티모듈 설계**: Composite Build, 의존성 관리
4. **데이터 접근**: JPA, R2DBC, JOOQ 비교
5. **이벤트 기반**: Kafka, RabbitMQ 메시징
6. **반응형 프로그래밍**: WebFlux, Reactor
7. **JDK 신기능**: Virtual Thread, Pattern Matching 등
8. **컨테이너 기반 개발**: Docker Compose

---

## 🔍 추천 탐색 경로

### 초급
1. [sample-app](prototype-hexagonal/bootstrap/sample-app) 실행
2. [SampleJpaEntity](prototype-hexagonal/module/sample) 엔티티 분석
3. REST API 테스트 (포트 29999)

### 중급
1. 헥사고날 구조 이해 (adapter → domain → use case)
2. R2DBC vs JPA 비교
3. Kafka 프로듀서/컨슈머 실습

### 고급
1. Spring AI RAG 구현 분석
2. 다중 데이터소스 라우팅 (router-config)
3. MCP 서버/클라이언트 구조
4. Virtual Thread 성능 테스트

---

*본 문서는 Claude Sonnet 4.5를 사용하여 자동 생성되었습니다.*
