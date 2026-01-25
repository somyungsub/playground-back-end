# Active Context

## Current Work Focus
- 프로젝트 초기 분석 및 메모리 뱅크 설정 완료
- 헥사고날 아키텍처 프로토타입 구조 확립됨

## Recent Changes
- security.md 추가 (최근 커밋)
- 헥사고날 테스트 추가
- 모듈 뎁스 조정
- 코드 정리 작업

## Active Decisions
- 헥사고날 아키텍처 패턴 채택
- 멀티 모듈 Gradle 구조 사용
- 커스텀 어노테이션(@UseCase, @WebAdapter, @PersistenceAdapter) 활용

## Current State
- 프로젝트 상태: **안정** (clean git status)
- 메인 브랜치: `main`
- 실행 가능한 부트스트랩 앱: 8개

## Next Steps
- 필요에 따라 새로운 도메인 모듈 추가
- 테스트 커버리지 확대
- JDK 21 Virtual Threads 테스트 확장

## Known Issues
- 현재 알려진 이슈 없음

## Notes
- 메인 애플리케이션은 `sample-app` (포트 29999)
- 설정 파일들은 각 config 모듈의 resources에 위치