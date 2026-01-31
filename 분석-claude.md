# 프로젝트 분석: playground-back-end

## 1. 프로젝트 개요

| 항목 | 설명 |
|------|------|
| **프로젝트 타입** | 멀티모듈 Gradle Composite Build |
| **주 언어** | Java 17, 21 (Kotlin 1.9.10 병행) |
| **프레임워크** | Spring Boot 2.7.3, 3.2.10 |
| **아키텍처** | 헥사고날 아키텍처 + AI 통합 |
| **빌드 도구** | Gradle 8.10.2 |
| **총 Java 파일** | ~280개 |

---

## 2. 디렉토리 구조

```
playground-back-end/
├── common-dependencies/        # 공통 의존성 모음
│   ├── spring-boot-2/
│   ├── spring-boot-3/
│   └── spring-boot-3-data/
├── spring-ai/                  # AI/MCP 실험 모듈
│   ├── basic/                  # Spring AI 기본 예제
│   └── mcp/                    # MCP (Model Context Protocol)
│       ├── server/
│       └── client/
├── prototype-hexagonal/        # 헥사고날 아키텍처 프로토타입
│   ├── bootstrap/              # 실행 애플리케이션들
│   ├── module/                 # 도메인 모듈들
│   ├── module-test/            # 테스트 모듈
│   ├── config/                 # 기술 설정 모듈
│   └── docker/                 # Docker Compose 설정
└── dev-diary-log/             # 개발 일기 기록 모듈
```

---

## 3. 주요 모듈 구성

### 3.1 common-dependencies (공통 의존성)

공통 라이브러리 관리 모듈:
- Jackson 2.17.2 (JSON 직렬화)
- Apache Commons (Lang3, Collections4, Math3)
- Guava 31.1-jre
- Netty resolver (macOS 지원)

### 3.2 spring-ai (AI 학습/실험)

**포트**: 9090

**주요 기능**:
- LLM Chat (Ollama/OpenAI)
- RAG (Retrieval-Augmented Generation)
- Tool Calling (외부 API 연동)
- Emotion Evaluation (감정 분석)
- VectorStore (Elasticsearch 기반)
- MCP (Model Context Protocol) 서버/클라이언트

**패키지 구조**:
```
springai/
├── config/           # 설정 클래스
├── controller/       # REST API
├── service/          # 비즈니스 로직
│   ├── impl/
│   ├── tool/
│   └── data/
└── SpringAiApplication.java
```

### 3.3 prototype-hexagonal (헥사고날 아키텍처)

**포트**: 29999

#### 도메인 모듈 (module/)

| 모듈 | 설명 |
|------|------|
| sample | 기본 샘플 도메인 (JPA/R2DBC, Kotlin) |
| order | 주문 관리 (Redis 캐시 포함) |
| membership | 회원 관리 |
| auth | 인증/권한 |
| staging | 스테이징 데이터 |
| engine | 엔진 |
| management | 관리 |
| router-db | 다중 DB 라우팅 |
| common | 공통 유틸 |

#### Bootstrap 애플리케이션

| 앱 | 설명 |
|----|------|
| sample-app | 핵심 샘플 앱 |
| web-test-app | 웹 테스트 앱 |
| consumer-app | Kafka/RabbitMQ 컨슈머 |
| producer-app | Kafka/RabbitMQ 프로듀서 |
| router-db-app | 다중 DB 라우팅 테스트 |
| node-server-app | Node.js Express 웹서버 |

#### 기술 설정 모듈 (config/)

| 모듈 | 설명 |
|------|------|
| jooq-config | JOOQ (타입안전 SQL) |
| r2dbc-config | R2DBC (반응형 DB) |
| redis-config | Redis 캐시 |
| rabbit-mq-config | RabbitMQ 메시지 |
| router-config | 다중 DB 라우팅 |
| kafka-config | Kafka 이벤트 |

---

## 4. 헥사고날 아키텍처 구조

각 도메인 모듈은 다음 구조를 따름:

```
[module]/[domain]/
├── adapter/                    # 외부 어댑터
│   ├── in/
│   │   └── web/               # REST 컨트롤러
│   └── out/
│       └── persistence/       # DB 어댑터
├── domain/                     # 도메인 핵심
│   ├── model/                 # 도메인 모델
│   ├── service/               # 도메인 서비스
│   └── vo/                    # Value Object
└── [module]UseCase.java       # Use Case 정의
```

---

## 5. API 엔드포인트

### Spring AI (포트 9090)

```
POST /tool/call          # Tool Calling API
POST /tool/stream        # 스트리밍 (SSE)
POST /tool/emotion       # 감정 평가

POST /rag/call           # RAG 기반 응답
POST /rag/stream         # 스트리밍 (SSE)
POST /rag/emotion        # 감정 평가

POST /chat               # 기본 채팅
POST /ai                 # AI 기본 API
```

### Prototype-Hexagonal (포트 29999)

```
GET/POST/PUT/DELETE /sample/{id}   # 샘플
GET/POST/PUT/DELETE /member/{id}   # 회원
GET/POST/PUT/DELETE /order/{id}    # 주문
```

---

## 6. 데이터베이스 엔티티

### SampleJpaEntity
```java
- id: Long
- name: String
- code: String
- inputEntities: List<SampleInputEntity> (OneToMany)
```

### MemberJpaEntity
```java
- id: long
- name: String
- password: String
- age: int
- email: String
- address: String
- registrationType: String
- phoneNumber1, phoneNumber2: String
- ip: String
```

### OrderJpaEntity
```java
- id: Long
- orderNo: String
- orderDate: LocalDateTime
- member: MemberJpaEntity (ManyToOne)
```

---

## 7. 주요 의존성

### 공통
- Spring Boot 2.7.3 / 3.2.10
- Jackson 2.17.2
- Lombok 1.18.34
- MapStruct 1.5.5.Final

### Spring AI
- Spring AI BOM 1.0.2
- Ollama, OpenAI 모델
- Elasticsearch 8.18.0

### 데이터 접근
- JPA (Hibernate)
- R2DBC
- JOOQ
- MongoDB
- Redis

### 메시징
- Kafka
- RabbitMQ

### 테스트
- JUnit 5
- Mockito 5.18.0
- TestContainers

---

## 8. 설정 파일

### settings.gradle (Composite Build)
```gradle
rootProject.name = 'playground-back-end'
includeBuild("common-dependencies")
includeBuild("spring-ai")
includeBuild("prototype-hexagonal")
includeBuild("dev-diary-log")
```

### Spring AI application.yml
```yaml
server:
  port: 9090
spring:
  ai:
    model:
      chat: ollama
      embedding: ollama
    ollama:
      chat:
        options:
          model: mistral
```

### Sample-app application.yml
```yaml
server:
  port: 29999
spring:
  config:
    import:
      - application-core.yml
      - application-rdb.yml
      - application-redis.yml
      - application-kafka.yml
```

---

## 9. Docker 지원

`prototype-hexagonal/docker/` 디렉토리:

- `docker-compose-db.yml` - PostgreSQL, MySQL, Oracle
- `docker-compose-kafka.yml` - Kafka (단일)
- `docker-compose-kafka-multi.yml` - Kafka (클러스터)
- `docker-compose-mongo-rs.yml` - MongoDB Replica Set
- `docker-compose-rabbitmq.yml` - RabbitMQ

---

## 10. 테스트 구조

### JDK Test (module-test/jdk-test)
- JDK 9-21 신규 기능 테스트
- Optional, ProcessHandle, Virtual Thread 등

### Async Test (module-test/async-test)
- 비동기 처리 테스트
- JOOQ, JDBC 테스트
- 이벤트 기반 처리

### 테스트 프레임워크
- JUnit 5 (Jupiter)
- Mockito
- JUnit Pioneer
- Spring Boot Test
- TestContainers

---

## 11. 아키텍처 다이어그램

### 헥사고날 아키텍처
```
       IN (어댑터)
    ↙          ↘
[Web REST]  [Message Queue]
    ↓          ↓
  ┌──────────────────┐
  │   USE CASE       │
  │ (비즈니스 로직)   │
  └──────────────────┘
    ↓          ↓
[Persistence]  [Cache]
    ↙          ↘
    OUT (어댑터)
```

### Spring AI 흐름
```
User Input
    ↓
[Controller] → [Service] → [Advisor]
                              │
    ├─→ [ChatMemory] (대화 히스토리)
    ├─→ [VectorStore] (RAG 검색)
    └─→ [Tool Calling] (외부 API)
                              ↓
                    [Chat Model] (Ollama/OpenAI)
                              ↓
                         Response
```

---

## 12. 프로젝트 특징 요약

1. **멀티모듈 Composite Build**: 4개의 독립적 빌드 모듈로 구성
2. **헥사고날 아키텍처**: 비즈니스 로직과 외부 기술 분리
3. **Spring AI 통합**: LLM, RAG, Tool Calling, MCP 지원
4. **다층 데이터 접근**: JPA, R2DBC, JOOQ, MongoDB, Redis
5. **이벤트 기반**: Kafka, RabbitMQ 지원
6. **JDK 버전 다양화**: Java 17, 21 병행 사용
7. **Docker 기반 인프라**: 로컬 개발 환경 완비

---

*분석 일자: 2026-01-31*
*분석 도구: Claude Code (Opus 4.5)*
