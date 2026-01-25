# System Architecture

## Architecture Pattern: Hexagonal (Ports & Adapters)

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

## Source Code Paths

### Module Structure
```
prototype-hexagonal/
├── module/                          # 도메인 모듈
│   ├── common/                      # 공통 기능
│   │   ├── adapter/                 # 공통 어댑터
│   │   ├── annotation/              # @UseCase, @WebAdapter 등
│   │   ├── exception/               # 예외 처리
│   │   └── response/                # API 응답 포맷
│   ├── membership/                  # 회원 도메인
│   │   ├── domain/                  # Member, Email VO
│   │   ├── application/             # MembershipUseCase, Service
│   │   └── adapter/                 # Web, Persistence Adapter
│   ├── order/                       # 주문 도메인
│   │   ├── domain/                  # Order, Payment, OrderStatus
│   │   ├── application/             # OrderUseCase, Service
│   │   └── adapter/                 # Web, Persistence Adapter
│   └── sample/                      # 샘플 도메인
├── config/                          # 설정 모듈 (플러그인 방식)
│   ├── rdb-config/                  # JPA 설정
│   ├── r2dbc-config/                # R2DBC 설정
│   ├── redis-config/                # Redis 설정
│   ├── kafka-config/                # Kafka 설정
│   └── ...
├── bootstrap/                       # 실행 애플리케이션
│   ├── sample-app/                  # 메인 앱
│   └── ...
└── module-test/                     # 테스트 모듈
    └── jdk-test/                    # JDK 기능 테스트
```

### Key File Locations
| 파일 유형 | 경로 패턴 |
|----------|----------|
| 도메인 엔티티 | `module/{domain}/domain/*.java` |
| UseCase 인터페이스 | `module/{domain}/application/port/in/*UseCase.java` |
| Service 구현 | `module/{domain}/application/service/*Service.java` |
| Web Adapter | `module/{domain}/adapter/in/web/*WebAdapter.java` |
| Persistence Adapter | `module/{domain}/adapter/out/persistence/*OutPortAdapter.java` |
| 설정 클래스 | `config/{type}-config/src/main/java/**/*Config.java` |

## Key Technical Decisions

### 1. 커스텀 어노테이션
- `@UseCase`: 비즈니스 로직 서비스 마킹 (Spring `@Service` 포함)
- `@WebAdapter`: HTTP 엔드포인트 마킹 (Spring `@RestController` 포함)
- `@PersistenceAdapter`: DB 접근 어댑터 마킹 (Spring `@Repository` 포함)

### 2. 포트 인터페이스
```java
// Inbound Port
CommandUseCase<Domain, Command, ID> {
    Domain save(Command);
    Domain update(ID);
    void delete(ID);
}

QueryUseCase<Domain, Query, ID> {
    Domain findById(ID);
    List<Domain> findAll();
}

// Outbound Port
CommandOutPort<Domain, ID>
QueryOutPort<Domain, ID>
```

### 3. 매핑 전략
- MapStruct를 사용한 객체 변환
- Request → Command → Domain → Entity 흐름
- Entity → Domain → Response 역방향 흐름

## Design Patterns in Use
- **Hexagonal Architecture**: 포트와 어댑터 패턴
- **Domain-Driven Design**: 도메인 중심 설계
- **Value Object**: Email, Payment 등 불변 객체
- **Command/Query Separation**: 명령과 조회 분리

## Component Relationships

### Order 생성 흐름
```
POST /v1/orders
    ↓
OrderWebAdapter.order(OrderRequest)
    ↓ MapStruct
OrderUseCase.orderProduct(ProductOrderCommand)
    ↓ OrderService (@UseCase)
Order.withoutId() [도메인 생성/검증]
    ↓
OrderOutPort.order(Order, memberId)
    ↓ OrderOutPortAdapter (@PersistenceAdapter)
OrderJpaEntity 변환 → JPA 저장
    ↓
MembershipOutPort.findById(memberId)
    ↓
Order.withMember(Member)
    ↓
ApiResponse.ok(OrderResponse)
```

## Critical Implementation Paths

### 새 도메인 추가 시
1. `module/{new-domain}/domain/` - 도메인 엔티티 생성
2. `module/{new-domain}/application/port/in/` - UseCase 인터페이스 정의
3. `module/{new-domain}/application/service/` - Service 구현 (@UseCase)
4. `module/{new-domain}/application/port/out/` - OutPort 인터페이스 정의
5. `module/{new-domain}/adapter/in/web/` - WebAdapter 구현 (@WebAdapter)
6. `module/{new-domain}/adapter/out/persistence/` - PersistenceAdapter 구현
7. `settings.gradle`에 모듈 추가
8. 부트스트랩 앱의 `build.gradle`에 의존성 추가

### 새 설정 모듈 추가 시
1. `config/{new}-config/` 디렉토리 생성
2. `build.gradle` 작성 (의존성 정의)
3. 설정 클래스 작성 (`*Config.java`)
4. `application-{new}.yml` 작성
5. `settings.gradle`에 모듈 추가