#
# UGO's Dev Blog API Server

### 목차

[1. 프로젝트 설명](#1-프로젝트-설명 )
<br/>
[2. 사용 라이브러리](#2-사용-라이브러리)
<br/>
[3. 주요 기능](#3-주요-기능)
<br/>
[4. 주요 기능별 설명](#4-주요-기능별-설명)
<br/>

#
## 1. 프로젝트 설명
#
![로고 이미지](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FEACL2%2FbtrnfBYVtgA%2FL4dVWu9Dv4RddOZrhwxBrK%2Fimg.png)


공부하는 것들을 정리해 놓을 수 있는 저만의 블로그를 만들어 보고 싶어 시작한 프로젝트입니다. Spring-boot 기반으로 구현되었으며 , 
Jenkins (구축 서버) , Docker (배포 서버) 를 통해 CI/CD가 구현되어 있습니다.        
<br/>

> API Sever URL

- https://www.ugosdevblog.com

<br/>

---

#
## 1. 프로젝트 구조
#









#
## 2. 사용 라이브러리
#
<br/>

|                 |Back-end Stack|
|-----------------|--------|  
 Language        | JAVA 11
 Framwork        | Spring-boot 2.4.2
 Library         | Spring-Security ,Spring-data-JPA
 Web Container   | Tomcat 9.0.41
 Database        | AWS RDS MariaDB 10.5.12
 Test DB         | H2 Database
 Hosting         | AWS EC2 Ubuntu Server
 Version Control | Git , GitHub
 etc             | QueryDsl , Javax Mail , Lombock


#
## 3. 주요 기능
#

- 이메일 인증을 통한 회원가입
- JWT token 을 활용한 로그인 , 로그인 유지
- Spring security를 통한 인증 및 인가
- Spring-data-JPA를 통한 연관관계 맵핑 , 쿼리 생성
- QueryDSL을 사용한 동적 쿼리 생성(검색 , 페이징)

#
## 4. 주요 기능별 설명
#

> ### 이메일 인증을 통한 회원가입
- Java Mail 을 활용하여 인증번호 이메일 전송



#
> ### JWT token을 활용한 로그인 , 로그인 유지

- 유저 인증 후 JWT 토큰 발급 혹은 JWT 토큰 유효성 확인
- access token , refresh 토큰을 이용한 로그인 유지

<small>- 로그인 인증 필터 로직</small>

```java
```


#

> ### Spring-Security를 통한 인증 및 인가
- Spring security 커스텀 필터를 통한 JWT 토큰 방식 유저인증

<small>- 로그인 토큰 검사 로직</small>

```java
```


#

> ### Spring-Data-JPA를 통한 연관관계 맵핑 , 쿼리 생성



#

> ### QueryDSL을 사용한 동적 쿼리 생성(검색 , 페이징)



#
