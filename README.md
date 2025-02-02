# 가상 암호화폐 거래 애플리케이션

Spring Boot를 기반으로 구축된 가상 암호화폐 거래 애플리케이션 백엔드로, JWT 인증을 사용하여 안전하고 효율적인 거래 환경을 제공합니다.

## 주요 기능

- **JWT 기반 인증**: 모든 REST API 요청은 JWT 토큰을 사용하며, 별도로 사용자 정보를 전송하지 않습니다.
- **사용자 인증 및 권한 관리**: Spring Security를 활용한 안전한 로그인 및 역할 기반 접근 제어.
- **거래 기능**: 사용자는 가상 환경에서 암호화폐를 매수 및 매도할 수 있습니다.
- **포트폴리오 관리**: 계정 잔액 및 거래 내역 조회 기능 제공.
- **시장 데이터 처리**: 실시간 또는 시뮬레이션된 시장 가격을 가져와 표시.
- **보안 조치**: 토큰 기반 인증 및 API 보안 모범 사례 적용.

## 기술 스택

- **백엔드**: Spring Boot, Spring Security, Spring Data JPA, QueryDSL
- **인증 방식**: JWT (JSON Web Token)
- **테스트**: JUnit 5
- **데이터베이스**: MariaDB

## 설치 방법

### 사전 요구 사항
- Java 17+
- Gradle
- MariaDB

### 설치 과정
1. 저장소를 클론합니다:
   ```sh
   git clone https://github.com/yourusername/crypto-trading-app.git
   cd crypto-trading-app
   ```
2. 애플리케이션 설정 파일 수정:
   `src/main/resources/application.properties`에서 데이터베이스 및 JWT 시크릿 키를 설정하세요.

## API 엔드포인트

### **유저 컨트롤러**
- **[POST]** `/api/user/join` → 회원가입
- **[POST]** `/api/user/change-password` → 비밀번호 변경
- **[GET]** `/api/user/info` → 사용자 정보 조회

### **거래 컨트롤러**
- **[POST]** `/api/transaction/create` → 거래 생성
- **[GET]** `/api/transaction/get` → 거래 내역 조회

### **보유 자산 컨트롤러**
- **[GET]** `/api/hold/get` → 보유 자산 조회

### **암호화폐 컨트롤러**
- **[GET]** `/api/crypto/price/{symbol}` → 특정 암호화폐 가격 조회
- **[GET]** `/api/crypto/candles/{symbol}` → 특정 암호화폐 캔들 데이터 조회

## 기여하기
개선 사항이 있다면 이슈를 등록하거나 풀 리퀘스트를 제출해주세요!

## 라이선스
MIT 라이선스

