# Product Context

## Why This Project Exists
이 프로젝트는 헥사고날 아키텍처(Hexagonal Architecture)의 실무 적용 가능한 완전한 구현 예제를 제공하기 위해 존재합니다. 단순한 예제가 아닌, 실제 프로덕션 환경에서 사용할 수 있는 수준의 아키텍처 패턴을 보여줍니다.

## Problems It Solves

### 1. 아키텍처 학습의 어려움
- 헥사고날 아키텍처의 개념은 이해하지만 실제 구현에 어려움을 겪는 개발자들을 위한 참고 구현 제공
- 포트와 어댑터 패턴의 실제 적용 방법 시연

### 2. 기술 스택 연동 테스트
- 다양한 데이터베이스(JPA, R2DBC, MongoDB, JOOQ)를 하나의 프로젝트에서 테스트 가능
- 메시징 시스템(Kafka, RabbitMQ, Redis Pub/Sub) 연동 실습 환경 제공

### 3. 모듈화된 설계
- 37개의 독립 모듈을 통해 관심사 분리와 플러그인 방식의 설정 추가/제거 시연
- 마이크로서비스 전환을 위한 기반 구조 제공

## How It Should Work

### 핵심 도메인
- **Membership**: 회원 관리 (가입, 조회, Email 검증)
- **Order**: 주문 처리 (생성, 조회, 상태 관리)
- **Sample**: 다양한 DB 연동 테스트용 도메인

### 데이터 흐름
```
HTTP Request → Web Adapter → UseCase → Domain → Persistence Adapter → Database
                                              → Event Adapter → Message Queue
```

### 부트스트랩 애플리케이션
- **sample-app**: 메인 통합 애플리케이션 (포트 29999)
- **consumer-app**: 메시지 컨슈머
- **producer-app**: 메시지 프로듀서
- **node-server-app**: Node.js 서버 (Express.js)

## User Experience Goals
- 코드만 보고도 아키텍처 패턴을 이해할 수 있어야 함
- 새로운 도메인/기능 추가 시 기존 패턴을 쉽게 따를 수 있어야 함
- 설정 모듈 추가/제거로 기술 스택을 유연하게 변경 가능해야 함