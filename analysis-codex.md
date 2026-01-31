# playground-back-end 분석 (Codex)

## 1) 프로젝트 개요
- **Gradle composite build** 형태: 최상위 `settings.gradle`에서 `common-dependencies`, `spring-ai`, `prototype-hexagonal`, `dev-diary-log`를 `includeBuild`로 연결.
- 최상위 `build.gradle`은 저장소(repositories)만 정의하고, 실제 빌드는 하위 composite에 위임.
- 최상위 `README.md`는 `dev-diary-log`만 간단히 설명.

## 2) 구성 모듈 요약

### A. common-dependencies (공통 의존성 모음)
- 목적: 공통 라이브러리를 한 곳에서 버전/의존성 관리.
- 버전
  - Spring Boot 2: `2.7.3`
  - Spring Boot 3: `3.2.10`
- 모듈
  - `spring-boot-2`: Boot 2 기반 기본 스타터(web, webflux, validation 등)
  - `spring-boot-3`: Boot 3 기반 기본 스타터
  - `spring-boot-3-data`: Boot 3 + JPA/Mongo/Redis
- 공통 라이브러리 예시: Jackson 2.17.2, Apache Commons, Guava, Netty macOS native resolver

### B. spring-ai (Spring AI 학습/실험용)
- 목적: Spring AI, MCP, Ollama/OpenAI 테스트
- 기본 버전
  - Spring Boot `3.2.10`
  - Java toolchain `21`
- 모듈
  - `basic`: Spring AI 기본 예제
  - `mcp`: MCP 관련 모듈
    - `mcp:server`: MCP Server (bootJar 생성, `mcp-server.jar`로 rootDir에 출력)
    - `mcp:client`: MCP Client
- Spring AI BOM: `1.0.2`
- 주요 의존성: OpenAI/Ollama, VectorStore(Elasticsearch), RAG, MCP
- 비고: `spring-ai/build.gradle`은 주석 처리되어 있으며 실제 빌드는 하위 모듈에서 수행

### C. prototype-hexagonal (헥사고날 아키텍처 프로토타입)
- 목적: Hexagonal/멀티모듈 구조 예시와 다양한 기술 스택 실험
- 기본 버전
  - Spring Boot `2.7.3`
  - Java toolchain `17`
  - Kotlin `1.9.10`
- 구조
  - `module`: 실제 도메인 모듈 (예: `auth`, `engine`, `management`, `membership`, `order`, `router-db`, `sample`, `staging`, `common`)
  - `module-test`: 테스트 모듈(consumer/producer/router/async/jdk)
  - `config`: 기술 설정 모듈(예: rdb, r2dbc, kafka, redis, jooq 등)
  - `bootstrap`: 실행/샘플 앱 모듈 (sample-app, web-test-app, consumer/producer-app 등)
  - `bootstrap/node-server-app`: 별도 Node.js 웹서버(express) 예제
- 공통 빌드 특징
  - `module` 하위 모듈에 JPA/R2DBC/Mongo/Redis/Kafka, QueryDSL 설정 포함
  - QueryDSL 코드 생성 태스크(`generateQueryDSL`) 자동 연결
  - `bootJar` 비활성화, `jar` 활성화 (library 모듈 성격)
- README에 실행/환경 정보 정리:
  - JDK 17, Spring Boot 2.7.3, Gradle 8.10.2, Kotlin 1.9.10, Node 16.19.1
  - `local` 프로파일 필수 및 `application-*.yml` import 구성 예시 제공

### D. dev-diary-log
- 목적: 개발 일기 기록용으로 보이는 간단 모듈
- 구성
  - `library`, `spring-boot`
  - 둘 다 `common-dependencies`와 `spring-boot-starter:3.2.10` 사용
- **주의**: `dev-diary-log/settings.gradle`에 `spring` 모듈을 include하지만 실제 디렉토리는 존재하지 않음

## 3) 기술 스택/버전 요약
- 빌드: Gradle (composite build)
- Java
  - `prototype-hexagonal`: 17
  - `spring-ai`: 21
- Spring Boot
  - 공통 의존성 모듈: 2.7.3 / 3.2.10
  - `prototype-hexagonal`: 2.7.3
  - `spring-ai`, `dev-diary-log`: 3.2.10
- 테스트: JUnit 5, Mockito, JUnit Pioneer
- 기타: Lombok, MapStruct, QueryDSL, Netty, Kafka, RDB/R2DBC, Redis, Mongo

## 4) 빌드/실행 포인트
- 최상위에서 composite build로 연결되어 있어 각 sub-build에서 모듈 단위로 빌드/실행 가능.
- `prototype-hexagonal` README에 실행 프로파일과 주요 앱(sample/web-test/consumer/producer 등) 안내.
- `spring-ai`는 `mcp:server`에서 `bootJar` 생성 시 결과가 rootDir(`spring-ai`)에 `mcp-server.jar`로 떨어지도록 설정.

## 5) 확인된 이슈/불일치
- `dev-diary-log/settings.gradle`에 포함된 `spring` 모듈 디렉토리가 실제로 없음.
- 최상위 `README.md`는 전체 프로젝트가 아닌 `dev-diary-log` 설명만 존재.

## 6) 요약
- **playground-back-end**는 여러 실험성 모듈을 한 저장소에서 관리하는 **Gradle composite build** 구조.
- 핵심 축은 `prototype-hexagonal`(헥사고날 구조 실험)과 `spring-ai`(AI/MCP 실험), 공통 의존성은 `common-dependencies`로 통합.
- 버전/스택이 공존(BOOT2/BOOT3, Java 17/21)하므로 모듈별 환경 분리가 중요.
