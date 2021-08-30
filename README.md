
## 개요

- 실시간 과일, 채소 가격을 조회할 수 있는 API 중계 웹 서버 어플리케이션을 개발
---
## 실행 방법

TODO

---

## 요구사항 정리

### API 개발

- [ ] access token 발급
  - [ ] 과일 가게
  - [ ] 채소 가게
- [ ] 목록 조회
  - [ ] 과일 목록
  - [ ] 채소 목록
- [ ] 가격 조회
  - [ ] 과일 가격
  - [ ] 채소 가격
- [ ] 기타
  - [X] api 주소 인코딩
  - [ ] 토큰 관리  
    - [ ] 토큰을 remote 저장소에 저장
    - [ ] 도커 컴포즈
  
### 브라우저

![images/main.png](image/main.png)

- [ ] 상품의 분류를 선택할 수 있는 기능 제공
  - [ ] 채소/과일 드롭다운 제공
  - [ ] 상품 목록 테이블
    - [ ] 채소 목록 불러오기
    - [ ] 과일 목록 불러오기
- [ ] 상품의 분류를 선택한 뒤 이름을 입력하여 조회 버튼 클릭시 현재 가격 제공하는 기능
  - [ ] 입력폼
  - [ ] 조회
  - [ ] 현재가격 출력
---
## 개발 환경

- IDE : IntelliJ
- OS : Window 10
---
## 기술 스택

- Java 11
- Spring Boot 2.5.4
- Spring Data Redis  
- Thymeleaf
- Lombok
---
## API 스펙

TODO

---
## 고려 사항
- API 확장성
  - 상품별 다른 기능이 추가될 때
  - 새로운 상품군이 추가될 때  

---
### 커밋 컨벤션

[Angular JS Commit Message conventions](https://gist.github.com/stephenparish/9941e89d80e2bc58a153#allowed-type) 을 따른다.

```text
- feat (feature)
- fix (bug fix)
- docs (documentation)
- style (formatting, missing semi colons, …)
- refactor
- test (when adding missing tests)
- chore (maintain)
```