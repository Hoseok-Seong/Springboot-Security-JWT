
# 🛡️ Springboot-Security-JWT

Spring Security 기반의 JWT 인증 시스템으로, Redis를 활용한 Refresh Token Rotation(RTR) 방식을 적용하여 **보안성과 실용성**을 모두 확보한 인증 구조를 구현한 프로젝트입니다.

---

## 🚀 프로젝트 개요

본 프로젝트는 JWT 인증 시스템에서 발생할 수 있는 **Refresh Token 탈취 리스크**를 줄이기 위해 Redis 기반의 RTR(Refresh Token Rotation) 전략을 적용하였습니다.  
로그인/회원가입/정보수정 API를 포함하며, 신규 Access Token 발급 시 기존 Refresh Token의 교체를 수행하여 **보안 위협에 능동적으로 대응**할 수 있도록 설계되었습니다.

---

## ✅ 주요 기능

- JWT 기반 로그인 / 회원가입 / 사용자 정보 수정 API
- Redis를 활용한 Refresh Token 저장 및 갱신
- Refresh Token Rotation (RTR) 방식으로 탈취 대응
- 유저 에이전트 기반 토큰 식별 및 보안 강화
- 테스트용 인증 API (`/api/test`)

---

## 🛠 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java |
| Framework | Spring Boot, Spring Security, Spring Data JPA |
| 인증/보안 | JWT (java-jwt), Spring Security |
| DB | MariaDB |
| In-memory | Redis |
| Dev | Lombok, Devtools |

---

## 🧩 주요 API 예시

```http
POST /login
POST /join
POST /api/user/update
POST /access-token
```

각 API는 유저 정보 처리 및 토큰 갱신 등 인증 흐름에 맞춰 구현되어 있습니다.

---

## 👨‍💻 담당 역할

- 전체 인증 흐름 설계 및 구현
- JWT 토큰 생성/검증 로직 구축
- Redis 기반 Refresh Token 저장소 및 갱신 처리
- RTR 로직 설계 및 보안 정책 수립
- 테스트용 Controller 및 사용자 인증 로직 작성

---

## 📝 트러블슈팅 & 인사이트

- Refresh Token이 Redis에 존재하지 않거나 일치하지 않을 경우 **강제 삭제 및 재로그인 유도** 로직으로 보안성 강화
- 토큰 갱신시 기존 토큰과 비교 후 탈취 가능성을 고려한 대응 로직 삽입

---

## ⚙️ 실행 방법

```bash
# Redis, MariaDB 설정 필요
./gradlew build
java -jar build/libs/springboot-security-jwt.jar
```
