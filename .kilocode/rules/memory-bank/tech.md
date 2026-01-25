# Technical Stack

## Core Technologies

### Language & Runtime
| 기술 | 버전 | 용도 |
|-----|------|------|
| Java | 17 | 메인 언어 |
| Node.js | - | node-server-app용 |

### Framework
| 기술 | 버전 | 용도 |
|-----|------|------|
| Spring Boot | 2.7.3 | 애플리케이션 프레임워크 |
| Spring Data JPA | - | ORM/데이터 접근 |
| Spring WebFlux | - | 리액티브 웹 |
| Spring Data R2DBC | - | 리액티브 DB 접근 |

### Build Tool
| 기술 | 버전 | 용도 |
|-----|------|------|
| Gradle | - | 빌드/의존성 관리 |

## Data Layer

### Databases
| 기술 | 용도 |
|-----|------|
| H2 | 개발/테스트 인메모리 DB |
| MySQL | 프로덕션 RDB |
| PostgreSQL | R2DBC 리액티브 DB |
| MongoDB | NoSQL 문서 DB |
| Redis | 캐시/Pub-Sub |

### ORM & Query
| 기술 | 버전 | 용도 |
|-----|------|------|
| JPA/Hibernate | - | ORM |
| R2DBC | - | 리액티브 DB 접근 |
| QueryDSL | - | 타입 안전 쿼리 |
| JOOQ | - | SQL 쿼리 빌더 |

## Messaging

| 기술 | 용도 |
|-----|------|
| Apache Kafka | 이벤트 스트리밍 |
| RabbitMQ | 메시지 큐 |
| Redis Pub/Sub | 실시간 이벤트 |

## Libraries

### Core
| 라이브러리 | 버전 | 용도 |
|-----------|------|------|
| Lombok | 1.18.34 | 보일러플레이트 제거 |
| MapStruct | 1.5.5 | 객체 매핑 |
| Jackson | 2.13.3 | JSON 직렬화 |
| Guava | 31.1 | 유틸리티 |
| Apache Commons Lang3 | - | 유틸리티 |
| Apache Commons Collections4 | - | 컬렉션 유틸리티 |

### Logging
| 라이브러리 | 버전 | 용도 |
|-----------|------|------|
| Log4j | 2.17.2 | 로깅 |
| Logback | - | Spring 기본 로깅 |

### Network
| 라이브러리 | 버전 | 용도 |
|-----------|------|------|
| Netty | 4.1.79 | 네트워크 프레임워크 |

### Testing
| 라이브러리 | 버전 | 용도 |
|-----------|------|------|
| JUnit 5 (Jupiter) | - | 테스트 프레임워크 |
| Mockito | 5.18.0 | 모킹 |
| JUnit Pioneer | 2.3.0 | JUnit 확장 |

### Node.js (node-server-app)
| 라이브러리 | 용도 |
|-----------|------|
| Express.js | 웹 프레임워크 |
| MongoDB Driver | MongoDB 연결 |
| Redis Client | Redis 연결 |

## Development Setup

### Prerequisites
- JDK 17+
- Gradle
- Docker (옵션: DB/메시징 실행용)
- Node.js (node-server-app용)

### Running the Application
```bash
# 메인 애플리케이션 실행
./gradlew :prototype-hexagonal:bootstrap:sample-app:bootRun

# 포트: 29999
```

### Project Structure
```
playground-back-end/
├── prototype-hexagonal/     # 메인 프로젝트
├── dev-diary-log/           # 개발 일기
├── docker/                  # Docker 설정
├── build.gradle             # 루트 빌드 설정
├── settings.gradle          # 37개 모듈 포함
└── gradle.properties        # 프로퍼티
```

## Technical Constraints

### Java Version
- 최소 Java 17 필요 (record, sealed class 등 사용)
- Virtual Threads 테스트는 JDK 21+ 필요

### Module Dependencies
- 도메인 모듈은 다른 도메인 모듈에 직접 의존하지 않음
- common 모듈에만 의존 가능
- 설정 모듈은 플러그인 방식으로 추가/제거

### Port Conventions
| 애플리케이션 | 포트 |
|-------------|------|
| sample-app | 29999 |

## Dependencies Management

### Gradle Multi-Module
- 루트 `build.gradle`: 공통 의존성 정의
- 각 모듈 `build.gradle`: 모듈별 의존성
- `settings.gradle`: 37개 모듈 동적 포함

### Configuration Files
| 파일 | 위치 | 용도 |
|-----|------|------|
| application.yml | bootstrap/*/resources | 메인 설정 |
| application-*.yml | config/*/resources | 환경별 설정 |
| logback-spring.xml | bootstrap/*/resources | 로깅 설정 |

## Tool Usage Patterns

### MapStruct
- `@Mapper(componentModel = "spring")` 사용
- Request → Command, Entity → Domain 변환

### Lombok
- `@Getter`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor` 주로 사용
- `@Data` 지양 (불변성 유지)

### Custom Annotations
```java
@UseCase          // @Service 포함
@WebAdapter       // @RestController 포함
@PersistenceAdapter // @Repository 포함
@EventConsumer    // 이벤트 컨슈머
@EventProducer    // 이벤트 프로듀서
@SelfValidating   // 자체 검증
```