# 🚀 (주)협진창호시스템 웹 애플리케이션 README

## 📖 프로젝트 개요
이 웹 애플리케이션은 **제품 소개**, **회사 정보 제공**, **고객 문의** 및 **사용자 인증** 기능을 포함하는 기업 홈페이지 및 관리 시스템입니다.
Spring Boot와 Vue.js를 기반으로 구축되었으며, Nginx를 리버스 프록시로 활용하여 프론트엔드(Vue.js)와 백엔드(Spring Boot) 간의 트래픽을 효과적으로 관리하고 있습니다.
또한, AWS 기반의 EC2, S3, RDS 등의 클라우드 서비스를 활용하여 안정적인 배포 환경을 구성하였으며, 외부 API와 연계하여 기능을 확장하였습니다.

🔗 **[🌐 프로젝트 웹사이트 바로가기](https://www.heopjin.com)**  

<img width="1265" alt="깃허브 리드미1" src="https://github.com/user-attachments/assets/20d3b362-e8a2-4348-aa06-274b3e246617" />


---

## ✨ 주요 기능

### 1️⃣ **제품 소개**
- AWS **S3 버킷**을 활용한 제품 이미지 및 파일 저장
- Vue.js 에서 **S3 이미지 URL을 불러와 동적 렌더링**

### 2️⃣ **회사 소개**
- **카카오맵 API**를 이용하여 회사 위치 지도 표시
- **V-Calendar**를 사용한 일정/이벤트 관리 기능 추가

### 3️⃣ **고객 문의**
- **SMTP 이메일 알림**을 통한 고객 문의 접수
(고객이 문의를 남기면 자동으로 관리자의 모든 계정에 등록된 이메일로 자동 알림 전송)
- **카카오 주소 API**를 활용한 편리한 주소 입력 기능

## 4️⃣ **공지 사항**
- **공지글 작성**을 통해 회사 소식 및 중요 알림 공유
- **TINYMCE EDITOR API**를 활용한 이미지 업로드, 다운로드 및 미리보기 기능 제공

## 5️⃣ **로그인 & 인증**
- **Spring Security + JWT** 기반의 인증 시스템
- JWT 토큰을 활용한 **관리자등록, 로그인, 접근 권한 관리**
- API 보안을 위한 **Access Token & Refresh Token** 사용
---

## 🛠 **사용한 기술 스택**
### 🔹 **백엔드 (Spring Boot)**
- Java 21 / Spring Boot 3.3.1
- Spring Security & JWT (사용자 인증 및 권한 관리)
- JPA (PostgraSQL 연동)
- QueryDSL (동적 쿼리)
- AWS S3 (파일 업로드 및 저장)
- SMTP (이메일 전송)
- Redis (Token Session 관리)

### 🔹 **프론트엔드 (Vue.js)**
- Vue.js 3 / Vite
- Vue Router (페이지 이동 관리)
- Axios (API 호출)
- V-Calendar (달력 UI)
- 카카오맵 API (회사 위치 표시)
- 카카오 주소 (주소 자동 입력)

### 🔹 **기타**
- AWS EC2 (배포)
- JUnit 5 / Swagger UI (테스트 환경)

## 🎯 추가 개선 예정 기능
- **월 별 문의 현황 차트 추가** 
- **V-Calender UI 개선**
- **메인 페이지 텍스트 커스터마이징 기능**
- **최근 제품 관리 이벤트 이력 확인**
- **견적 요청 기능 분리 및 실시간 알림 추가**
