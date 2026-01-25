# Project Brief

## Project Name
Playground Backend - Hexagonal Architecture Prototype

## Overview
Spring Boot 기반의 헥사고날 아키텍처 학습 및 프로토타입 프로젝트. 다양한 기술 스택(메시징, DB, 캐시)을 플러그인 방식으로 연동하고 테스트할 수 있는 플레이그라운드.

## Core Requirements
1. **헥사고날 아키텍처 구현**: 포트와 어댑터 패턴을 통한 도메인 중심 설계
2. **멀티 모듈 구조**: 37개의 독립적인 Gradle 모듈로 관심사 분리
3. **다중 데이터 소스 지원**: JPA, R2DBC, MongoDB, JOOQ 등 다양한 DB 연동
4. **이벤트 주도 설계**: Kafka, RabbitMQ, Redis Pub/Sub 메시징 통합
5. **마이크로서비스 준비**: 각 부트스트랩 앱이 독립 배포 가능한 구조

## Goals
- 헥사고날 아키텍처의 실무 적용 가능한 완전한 구현 예제 제공
- 다양한 기술 스택 연동 및 테스트를 위한 플레이그라운드 제공
- JDK 신기능(Virtual Threads 등) 테스트 환경 제공
- 마이크로서비스 아키텍처로 전환 가능한 기반 구축

## Target Users
- 헥사고날 아키텍처를 학습하고자 하는 개발자
- Spring Boot 기반 프로젝트의 아키텍처 설계 참고용
- 다양한 기술 스택 연동을 테스트하고자 하는 개발자