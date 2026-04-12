# Redis → PostgreSQL 변경 내용

## 바뀐 부분
- Redis 기반 로그인 실패 횟수 관리 제거
- Redis 기반 refresh token 저장소 제거
- PostgreSQL 테이블 기반으로 대체

## 새로 추가된 테이블
- `tb_login_attempts`
- `tb_refresh_tokens`

초기 SQL 파일:
- `src/main/resources/schema-postgresql-auth.sql`

## 설정 변경
- `build.gradle`에서 Redis starter 제거
- `application.yaml`에서 Redis 설정 제거
- `spring.sql.init.mode=always`로 인증용 보조 테이블 생성
- `.env.example`의 PostgreSQL JDBC URL 오타 수정

## 동작 방식
### 로그인 실패 제한
- 사용자별 실패 횟수와 윈도우 만료 시간을 `tb_login_attempts`에 저장
- 5회 이상 실패하면 `locked_until` 시점까지 로그인 차단
- 로그인 성공 시 기록 삭제

### Refresh Token
- refresh token 자체를 PK로 저장
- user_id FK로 사용자와 연결
- expires_at 기준으로 만료 판단
- 사용자 토큰 조회 시 만료 토큰은 먼저 정리

## 실행 전 확인
`.env` 예시

```env
JWT_SECRET_KEY=...
DB_URL=jdbc:postgresql://localhost:5432/board_crud
DB_USERNAME=postgres
DB_PASSWORD=postgres
DB_DRIVER_CLASS_NAME=org.postgresql.Driver
EMAIL_ADDRESS=your-email
EMAIL_PASSWORD=your-password
```

## 주의
- 현재 이 환경에서는 `JAVA_HOME` / Java 런타임이 없어서 `./gradlew test` 실행 검증은 못 함
- 코드 변경은 구조 기준으로 맞췄고, 실제 실행은 Java 17 환경에서 한 번 확인 필요
