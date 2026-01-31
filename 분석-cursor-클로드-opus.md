# Playground Backend 프로젝트 종합 분석 보고서

## 프로젝트 개요

| 항목 | 내용 |
|------|------|
| **프로젝트명** | playground-back-end |
| **유형** | Gradle 멀티 모듈 프로젝트 (Composite Build) |
| **목적** | 백엔드 아키텍처 실험, AI 기술 통합, 기술 학습 플랫폼 |
| **주요 언어** | Java 17/21, Kotlin (일부), Node.js (일부) |
| **프레임워크** | Spring Boot 2.7.x / 3.x, Spring AI 1.0.2 |

---

## 프로젝트 구조 개요

```
playground-back-end/
├── common-dependencies/     # 공통 의존성 관리 모듈
├── spring-ai/               # Spring AI 기반 AI 기능 실험
├── prototype-hexagonal/     # 헥사고날 아키텍처 프로토타입
├── dev-diary-log/           # 개발 학습 일기
├── build.gradle             # 루트 빌드 설정
├── settings.gradle          # includeBuild 설정
└── gradle.properties        # Gradle 속성
```

루트 프로젝트는 `includeBuild`를 통해 4개의 독립적인 빌드를 구성합니다:

```groovy
includeBuild("common-dependencies")
includeBuild("spring-ai")
includeBuild("prototype-hexagonal")
includeBuild("dev-diary-log")
```

---

## 모듈별 상세 분석

### 1. common-dependencies

**역할**: 프로젝트 전반에서 사용하는 공통 라이브러리 의존성을 중앙 집중화하여 관리합니다.

**구조**:
```
common-dependencies/
├── build.gradle           # 공통 의존성 정의
├── spring-boot-2/         # Spring Boot 2.x 전용 의존성
├── spring-boot-3/         # Spring Boot 3.x 전용 의존성
└── spring-boot-3-data/    # Spring Boot 3.x + Data 관련
```

**주요 의존성**:
| 라이브러리 | 버전 | 용도 |
|-----------|------|------|
| Jackson | 2.17.2 | JSON 직렬화/역직렬화 |
| Apache Commons IO | 2.11.0 | 파일 I/O 유틸리티 |
| Apache Commons Lang3 | 3.12.0 | 문자열/객체 유틸리티 |
| Apache Commons Collections4 | 4.4 | 컬렉션 유틸리티 |
| Guava | 31.1-jre | Google 유틸리티 |
| Netty (macOS native) | 4.1.79 | 네트워크 (M1 지원) |

---

### 2. spring-ai

**역할**: Spring AI 프레임워크를 활용한 AI/LLM 통합 실험 모듈입니다.

**기술 스택**:
- Spring AI 1.0.2 (BOM)
- OpenAI, Ollama (로컬 LLM)
- Elasticsearch Vector Store
- RAG (Retrieval Augmented Generation)
- MCP (Model Context Protocol)

**구조**:
```
spring-ai/
├── basic/                 # 기본 Spring AI 기능 테스트 (JDK 21)
├── mcp/
│   ├── server/            # MCP 서버 구현 (포트 9091)
│   └── client/            # MCP 클라이언트 구현
├── build.gradle
└── README.md              # Spring AI 학습 자료
```

**basic 모듈 주요 의존성**:
- `spring-ai-client-chat`: 채팅 클라이언트
- `spring-ai-starter-model-openai`: OpenAI 모델 통합
- `spring-ai-starter-model-ollama`: Ollama 로컬 LLM
- `spring-ai-rag`: RAG 구현
- `spring-ai-tika-document-reader`: 문서 파싱 (PDF, Office 등)
- `spring-ai-starter-vector-store-elasticsearch`: 벡터 저장소

**MCP 서버 주요 기능**:
```java
@Tool(description = "Spring AI 강의에 대해 RAG 기반으로 답변을 제공", returnDirect = true)
public String ragTool(@ToolParam(description = "Spring AI 강의에 대한 질문") String userPrompt) {
    return ragService.call(createPrompt(userPrompt), "MCP-SERVER", "")
            .getResult().getOutput().getText();
}
```

**설정 하이라이트** (application.yml):
```yaml
spring:
  ai:
    mcp:
      server:
        name: spring-ai-mcp-server
        type: SYNC
        sse-message-endpoint: /mcp/messages
    ollama:
      chat:
        options:
          model: hf.co/rippertnt/HyperCLOVAX-SEED-Text-Instruct-1.5B-Q4_K_M-GGUF
      embedding:
        options:
          model: bge-m3  # 다국어 지원 임베딩 모델
```

---

### 3. prototype-hexagonal

**역할**: 헥사고날 아키텍처(Ports & Adapters Pattern)를 완전하게 구현한 프로토타입 프로젝트입니다.

**기술 스택**:
| 항목 | 버전/기술 |
|------|----------|
| Spring Boot | 2.7.3 |
| Java | 17 |
| Kotlin | 1.9.10 (일부) |
| Lombok | 1.18.34 |
| MapStruct | 1.5.5 |
| Mockito | 5.18.0 |

**아키텍처 다이어그램**:
```
┌─────────────────────────────────────────────────────────────────┐
│                        ADAPTERS (외부)                           │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │ Web Adapter  │  │Redis Adapter │  │Event Adapter │          │
│  │  (HTTP API)  │  │  (Pub/Sub)   │  │(Kafka/Rabbit)│          │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘          │
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
│  └─────────────────────────┬───────────────────────────────┘   │
│                            ▼                                    │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │                   DOMAIN LAYER                           │   │
│  │          Entity, Value Object, Enumeration               │   │
│  └─────────────────────────┬───────────────────────────────┘   │
│                            ▼                                    │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │                   PORTS (Outbound)                       │   │
│  └─────────────────────────┬───────────────────────────────┘   │
│                            ▼                                    │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │  JPA Adapter │  │R2DBC Adapter │  │Mongo Adapter │          │
│  └──────────────┘  └──────────────┘  └──────────────┘          │
└─────────────────────────────────────────────────────────────────┘
```

**프로젝트 구조**:
```
prototype-hexagonal/
├── bootstrap/              # 실행 가능한 애플리케이션
│   ├── sample-app/         # 통합 앱 (포트 29999)
│   ├── consumer-app/       # 메시지 컨슈머
│   ├── producer-app/       # 메시지 프로듀서
│   ├── designer-app/       # 디자이너 앱
│   ├── router-db-app/      # DB 라우팅 테스트
│   ├── runtime-app/        # 런타임 테스트
│   ├── web-test-app/       # 웹 테스트
│   └── node-server-app/    # Node.js Express 서버
│
├── module/                 # 도메인 모듈
│   ├── common/             # 공통 (어노테이션, 예외, API 응답)
│   ├── membership/         # 회원 도메인 (완성)
│   ├── order/              # 주문 도메인 (완성)
│   ├── sample/             # 샘플 도메인 (63개 Java 파일)
│   ├── auth/               # 인증 (스켈레톤)
│   ├── engine/             # 엔진 (스켈레톤)
│   ├── management/         # 관리 (스켈레톤)
│   ├── staging/            # 스테이징 (스켈레톤)
│   └── router-db/          # DB 라우팅
│
├── config/                 # 설정 모듈 (플러그인 방식)
│   ├── core-config/        # 핵심 설정
│   ├── rdb-config/         # JPA/Hibernate
│   ├── r2dbc-config/       # 리액티브 DB
│   ├── nosql-config/       # MongoDB
│   ├── redis-config/       # Redis
│   ├── kafka-config/       # Kafka
│   ├── rabbit-mq-config/   # RabbitMQ
│   ├── jooq-config/        # JOOQ
│   ├── router-config/      # 동적 DB 라우팅
│   ├── swagger-config/     # Swagger API 문서
│   └── third-party-config/ # QueryDSL 등
│
├── module-test/            # 테스트 모듈
│   ├── async-test/         # 비동기 테스트
│   ├── consumer-test/      # 컨슈머 테스트
│   ├── producer-test/      # 프로듀서 테스트
│   ├── jdk-test/           # JDK 기능 테스트
│   └── router-test/        # 라우터 테스트
│
└── docker/                 # Docker Compose 파일
    ├── docker-compose-db.yml
    ├── docker-compose-kafka.yml
    ├── docker-compose-kafka-multi.yml
    ├── docker-compose-rabbitmq.yml
    └── docker-compose-mongo-rs.yml
```

#### 핵심 도메인 모듈

**Common Module** - 공통 컴포넌트:
| 컴포넌트 | 설명 |
|---------|------|
| `@UseCase` | 비즈니스 서비스 마킹 (Spring @Service 포함) |
| `@WebAdapter` | HTTP 엔드포인트 마킹 (Spring @RestController 포함) |
| `@PersistenceAdapter` | DB 접근 마킹 (Spring @Repository 포함) |
| `@EventConsumer` / `@EventProducer` | 이벤트 처리 마킹 |
| `ApiResponse<T>` | 표준 API 응답 래퍼 |
| `SelfValidating` | 자체 검증 추상 클래스 |
| `CommandUseCase` / `QueryUseCase` | 포트 인터페이스 |

**Membership Module** - 회원 도메인:
- **Domain**: `Member` (추상), `AdultMember`, `KidsMember`
- **Value Objects**: `Email`, `PhoneNumber`, `Address`
- **Enumeration**: `RegistrationType`
- **Domain Services**: `MemberFactory`, `MemberManager`
- **API**: `POST /v1/membership/join`, `GET /v1/membership/{id}`

**Order Module** - 주문 도메인:
- **Domain**: `Order`, `Payment` (VO), `OrderStatus` (Enum)
- **Command**: `ProductOrderCommand`
- **API**: `POST /v1/orders`, `GET /v1/orders/{id}`

---

### 4. dev-diary-log

**역할**: 개발 학습 일기 및 단위 기능 테스트용 모듈입니다.

**구조**:
```
dev-diary-log/
├── library/          # 라이브러리 테스트
│   └── src/main/java/md/jpa.md
├── spring-boot/      # Spring Boot 학습
│   └── src/main/java/md/
│       ├── config.md
│       ├── core.md
│       ├── security.md
│       └── test.md
└── settings.gradle
```

---

## 기술 스택 종합

### 언어 및 런타임

| 기술 | 버전 | 사용처 |
|------|------|--------|
| Java | 17 | prototype-hexagonal, spring-ai 기본 |
| Java | 21 | spring-ai/basic (Virtual Threads) |
| Kotlin | 1.9.10 | prototype-hexagonal 일부 |
| Node.js | - | node-server-app |

### 프레임워크

| 프레임워크 | 버전 | 용도 |
|-----------|------|------|
| Spring Boot | 2.7.3 | prototype-hexagonal |
| Spring Boot | 3.2.10+ | dev-diary-log, spring-ai |
| Spring AI | 1.0.2 | AI/LLM 통합 |
| Spring WebFlux | - | 리액티브 웹 |
| Spring Data JPA | - | ORM |
| Spring Data R2DBC | - | 리액티브 DB |
| Express.js | - | Node.js 서버 |

### 데이터베이스 및 메시징

| 기술 | 용도 |
|------|------|
| MySQL/H2 | 관계형 DB (JPA) |
| PostgreSQL | 리액티브 DB (R2DBC) |
| MongoDB | NoSQL 문서 DB |
| Redis | 캐시, Pub/Sub |
| Elasticsearch | Vector Store (AI) |
| Apache Kafka | 이벤트 스트리밍 |
| RabbitMQ | 메시지 큐 |

### 주요 라이브러리

| 라이브러리 | 버전 | 용도 |
|-----------|------|------|
| Lombok | 1.18.34 | 보일러플레이트 제거 |
| MapStruct | 1.5.5 | 객체 매핑 |
| Jackson | 2.17.2 | JSON 처리 |
| Log4j | 2.17.2 | 로깅 |
| Mockito | 5.18.0 | 테스트 모킹 |
| JUnit 5 | - | 테스트 프레임워크 |
| QueryDSL | - | 타입 안전 쿼리 |
| JOOQ | - | SQL 빌더 |

---

## 설계 패턴 및 원칙

### 적용된 패턴

1. **Hexagonal Architecture (Ports & Adapters)**
   - 도메인 중심 설계
   - 외부 의존성 역전
   - 테스트 용이성 확보

2. **Domain-Driven Design (DDD)**
   - Entity, Value Object, Domain Service
   - Aggregate, Enumeration
   - Ubiquitous Language

3. **Command/Query Separation (CQS)**
   - Command: 상태 변경 (Create, Update, Delete)
   - Query: 조회 전용

4. **Factory Pattern**
   - `MemberFactory`: 회원 타입별 생성

5. **Mapper Pattern**
   - MapStruct 기반 계층 간 객체 변환

6. **Plugin Architecture**
   - Config 모듈을 의존성으로 추가하면 자동 활성화

---

## 실행 방법

### 1. 인프라 실행 (Docker)
```bash
cd prototype-hexagonal/docker
docker-compose -f docker-compose-db.yml up -d
docker-compose -f docker-compose-kafka.yml up -d      # 선택
docker-compose -f docker-compose-rabbitmq.yml up -d   # 선택
```

### 2. 메인 애플리케이션 실행
```bash
./gradlew :prototype-hexagonal:bootstrap:sample-app:bootRun
```

### 3. Spring AI MCP 서버 실행
```bash
./gradlew :spring-ai:mcp:server:bootRun
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

---

## 프로젝트 특징 및 강점

### 1. 완성도 높은 헥사고날 아키텍처 구현
- 37개 이상의 Gradle 모듈로 관심사 분리
- 실무 적용 가능한 수준의 구조화

### 2. 다중 데이터 소스 지원
- JPA, R2DBC, MongoDB, JOOQ 동시 지원
- 도메인별 최적 DB 선택 가능

### 3. 이벤트 주도 설계
- Kafka, RabbitMQ, Redis Pub/Sub 통합
- 마이크로서비스 전환 준비 완료

### 4. 최신 AI 기술 통합
- Spring AI 1.0.2 기반 LLM 연동
- RAG, Vector Store, MCP 구현
- OpenAI, Ollama (로컬 LLM) 지원

### 5. 플러그인 방식 설정
- Config 모듈 추가/제거로 기능 활성화/비활성화
- 유연한 확장성

### 6. 학습 친화적 구조
- 스켈레톤 모듈 제공 (auth, engine, management, staging)
- 상세한 README 및 학습 자료 포함

---

## 향후 발전 방향

1. **인증/인가 모듈 완성**: `auth` 모듈 구현 (JWT, OAuth2)
2. **API Gateway 통합**: Spring Cloud Gateway 적용
3. **모니터링**: Spring Boot Actuator, Prometheus, Grafana
4. **CI/CD**: GitHub Actions, Docker 이미지 빌드
5. **Kubernetes 배포**: Helm Chart 작성

---

## 참고사항

- **최소 Java 버전**: 17 (일부 모듈 21 필요)
- **메인 앱 포트**: 29999
- **MCP 서버 포트**: 9091
- **권장 메모리**: 4GB+ (Ollama 사용 시 7GB+)

---

**작성일**: 2026-01-31  
**분석 도구**: Cursor (Claude Opus 4.5)  
**프로젝트 상태**: Active Development
