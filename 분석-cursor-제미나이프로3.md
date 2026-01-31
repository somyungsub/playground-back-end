# 프로젝트 분석 보고서 (Cursor + Gemini Pro 3)

## 1. 프로젝트 개요
- **프로젝트명**: `playground-back-end`
- **유형**: Gradle 멀티 모듈 프로젝트
- **목적**: 백엔드 기술 학습, 프로토타이핑, 아키텍처 실험을 위한 샌드박스 환경
- **주요 언어 및 프레임워크**: Java (17/21), Spring Boot (3.x), Gradle

## 2. 모듈 구성 및 상세 분석

루트 프로젝트는 크게 4개의 독립적인 빌드(`includeBuild`)로 구성되어 있습니다.

### 2.1. common-dependencies
- **역할**: 프로젝트 전반에서 사용되는 공통 의존성 및 버전을 관리합니다.
- **구조**:
  - `spring-boot-2`, `spring-boot-3`, `spring-boot-3-data` 등의 서브 모듈을 포함하여 Spring Boot 버전에 따른 의존성 그룹을 제공하려는 의도로 보입니다.
- **주요 라이브러리**: Jackson, Apache Commons (IO, Lang3, Collections), Guava, Netty (macOS native support 포함).

### 2.2. spring-ai
- **역할**: Spring AI 프레임워크를 활용한 AI 기능 실험 및 구현.
- **기술 스택**: Spring AI 1.0.2 (BOM 기준), OpenAI, Ollama, Elasticsearch (Vector Store), RAG (Retrieval Augmented Generation).
- **하위 모듈**:
  - `basic`: 기본 Spring AI 기능 테스트.
  - `mcp`: Model Context Protocol 관련 구현으로, `client`와 `server`로 나뉘어 있습니다.
    - `mcp/server`: RAG 서비스, Tool 기능 등을 포함.
    - `mcp/client`: MCP 서버와 통신하는 클라이언트 구현.

### 2.3. prototype-hexagonal
- **역할**: 헥사고날 아키텍처(Hexagonal Architecture)를 적용한 애플리케이션 프로토타입.
- **특징**:
  - 가장 복잡하고 체계적인 구조를 가지고 있으며, 도메인(`module`)과 인프라/설정(`config`), 실행 앱(`bootstrap`)이 명확히 분리되어 있습니다.
  - `settings.gradle`에 커스텀 스크립트를 사용하여 하위 디렉토리를 재귀적으로 모듈로 포함시킵니다.
- **구조**:
  - `bootstrap`: 실행 가능한 애플리케이션 모음 (`consumer-app`, `designer-app`, `producer-app`, `node-server-app` 등).
  - `config`: 기술별 설정 모듈 (`rdb`, `kafka`, `redis`, `mongo`, `rabbit-mq` 등).
  - `module`: 핵심 비즈니스 로직 (추정).
  - `module-test`: 다양한 시나리오 테스트 (`async`, `producer`, `router` 등).
  - `docker`: Kafka, Redis, MongoDB, RDB 등을 실행하기 위한 Docker Compose 파일 포함.

### 2.4. dev-diary-log
- **역할**: 개발 일기 및 학습 내용 기록, 단위 기능 테스트.
- **구조**:
  - `library`, `spring`, `spring-boot` 모듈로 구성.
  - Spring Boot 3.2.10을 기반으로 하며, 특정 라이브러리나 프레임워크 기능을 격리하여 테스트하는 용도로 보입니다.

## 3. 기술 스택 요약

| 카테고리 | 기술 | 비고 |
| --- | --- | --- |
| **Language** | Java 17, Java 21 | 모듈별로 다르게 설정됨 (Spring AI 등은 17 사용) |
| **Framework** | Spring Boot 3.x | 주력 프레임워크 |
| **AI** | Spring AI, OpenAI, Ollama | RAG, Vector Store 구현 포함 |
| **Build Tool** | Gradle | 멀티 모듈 및 `includeBuild` 사용 |
| **Architecture** | Hexagonal Architecture | `prototype-hexagonal` 모듈에 적용 |
| **Data & Messaging** | Kafka, RabbitMQ, Redis, MongoDB, Elasticsearch, MySQL/MariaDB (RDB) | Docker Compose로 환경 구성 |
| **Utils** | MapStruct, Lombok, Apache Commons | 생산성 및 유틸리티 |

## 4. 종합 의견
이 프로젝트는 단순한 백엔드 애플리케이션이 아니라, **기술 검증(PoC)과 아키텍처 실험을 위한 종합 플랫폼** 역할을 하고 있습니다.
특히 `spring-ai` 모듈을 통해 최신 AI 기술을 통합하려는 시도가 보이며, `prototype-hexagonal`에서는 MSA 환경을 고려한 메시징 시스템(Kafka, RabbitMQ)과 다양한 데이터 저장소(RDB, NoSQL, Redis)를 아우르는 복잡한 아키텍처를 헥사고날 패턴으로 구현하고 있습니다. `common-dependencies`를 통해 모듈 간 의존성 파편화를 방지하려는 구조적 고민도 엿보입니다.
