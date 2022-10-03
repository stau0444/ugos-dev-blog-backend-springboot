
# UGO's Dev Blog API Server

---
### 목차

---
[1. 프로젝트 설명](#1프로젝트-설명)

[2. 개발 환경](#2개발-환경)

[3. 프로젝트 구조](#3프로젝트-구조)

[4. ERD](#4ERD)

[5. 주요 구현 기능](#5주요-구현-기능)

[6. 요청 API 명세](#6요청-API-명세)

---

## 1.프로젝트 설명

![로고 이미지](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FEACL2%2FbtrnfBYVtgA%2FL4dVWu9Dv4RddOZrhwxBrK%2Fimg.png)


> 공부하는 것들을 정리해 놓을 수 있는 저만의 블로그를 만들어 보고 싶어 시작한 프로젝트입니다. `Spring-boot` 으로 만들어 졌으며 , 
`Jenkins` (빌드 서버) , `Docker` (배포 서버) 를 통해 CI/CD가 구현되어 있습니다.
기본적인 글 작성 , 삭제 , 수정 , 검색 ,  이미지 업로드 ,회원 가입  , 로그인/아웃 , 댓글 작성 기능들이 구현되어 있습니다.  


#### API Sever Domain

https://www.ugosdevblog.com

#### Swagger-ui 

https://www.ugosdevblog.com/swagger-ui/

(swagger-ui를 통해 api-spec을 확인할 수 있습니다.)

---

## 2.개발 환경

|                 | Back-end Stack                   |
|-----------------|----------------------------------|  
| Language        | `JAVA 11  `                        |
| CI/CD           | `Jenkins 2.366` , `Docker` 20.10.17 |
| Framwork        | `Spring-boot 2.4.2  `              |
| Library         | `Spring-Security ,Spring-data-JPA` |
| build Tool      | `gradle 6.6 `                      |
| Web Container   | `Tomcat 9.0.41    `                |
| Database        | `AWS RDS MariaDB 10.5.12`          |
| Test DB         | `H2 Database`                      |
| Build Server    |` AWS EC2 Ubuntu Server 22.04 LTS ` |
| Deploy Server    |` AWS EC2 Ubuntu Server 20.04.5 LTS `|
| Version Control | `Git , GitHub `                    |
| etc             | `QueryDsl , Javax Mail , Lombock`  |

---

## 3.프로젝트 구조

<img width="541" alt="프로젝트동작도" src="https://user-images.githubusercontent.com/51349774/193404346-a772a87f-30a1-4711-9431-eedc3351aa44.png">

---

## 4.ERD


<img width="541" alt="스크린샷 2022-10-03 오후 11 57 02" src="https://user-images.githubusercontent.com/51349774/193609285-d7ea512f-e442-44ed-9f84-45f2c46cd15b.png">



---

## 5.주요 구현 기능

- `AWS EC2` ,`Jenkins` , `dockerhub를` 활용한 배포자동화
- `JWT token` 을 활용한 로그인 , 로그인 유지
- 이메일 인증을 통한 회원가입
- `Spring security`를 통한 인증 및 인가
- `JPA를` 통한 연관관계 맵핑 , 쿼리 생성
- `QueryDSL`을 사용한 동적 쿼리 생성(검색 , 페이징)
- 블로그 글 작성 ,수정 , 삭제
- 댓글 작성 수정 , 삭제
- S3 이미지 업로드(user , content)  , 이미지 수정
- RDS MariaDB 데이터 저장

---

## 6.API 명세

### User

아이디 중복체크 -	`GET  /api/user/duplication-check`

이메일 인증 - `GET  /api/user/email-verify`

아이디 찾기 - `GET  /api/user/find-id`

비밀번호 찾기 -`GET  /api/user/find-pwd`

유저 정보 수정 - `PUT  /api/user/`

유저 추가 - `POST  /api/user/`

비밀번호 변경 - `PUT  /api/user/change-pwd`

### Content

컨텐츠 검색	- `GET  /api/content/search`

단일 컨텐츠	- `GET  /api/content/{contentId}`

컨텐츠 리스트 - `GET  /api/contents`

검색어 리스트 -	`GET  /api/whitelist`

컨텐츠 저장	- `POST  /api/content`

컨텐츠 수정	- `PUT  /api/content/{contentId}`

컨텐츠 삭제	- `DELETE  /api/content/{contentId}`

댓글 요청 - `GET  /api/content/{contentId}/comment`

댓글 저장 - `POST  /api/content/{contentId}/comment`


### Tag

태그 저장 - `POST  /api/tag`

태그리스트 요청 - `GET  /api/tags`

---

