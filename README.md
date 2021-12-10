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


공부하는 것들을 정리해 놓을 수 있는 저만의 블로그를 만들어 보고 싶어 시작한 프로젝트입니다. Spring-boot 기반으로 구현되었으며 , 현재 AWS EC2 인스턴스에 배포되어 있습니다 .
<br/>

> API Sever URL

<br/>

- https://www.ugosdevblog.com

<br/>




#
## 2. 사용 라이브러리
#

<br/>

Back-end Stack||
--|--  
Language | JAVA 11
Framwork | Spring-boot 2.4.2
Library| Spring-Security ,Spring-data-JPA
Web Container | Tomcat 9.0.41
Database | AWS RDS MariaDB 10.5.12
Test DB | H2 Database
Hosting | AWS EC2 Ubuntu Server
Version Control| Git , GitHub
etc | QueryDsl , Javax Mail , Lombock


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

<small>- 인증메일 전송 로직</small>

```java
 public int sendMailToUser(String userMail,String userName){
        Random random = new Random();
        int verifyNum = random.nextInt(99999);
        String AdminEmail = "ugosdevblog@gmail.com";
        String AdminPwd = "AsdAsd12!";
        String subject = "UGO'S Dev Blog에 요청하신 인증번호입니다.";
        String fromMail = "ugosdevblog@gmail.com";
        String fromName = "Ugo's Dev blog";
        String toMail = userMail;

        StringBuffer contents = new StringBuffer();
        if(userName == null){
            contents.append("<div style=\"width: 50%; padding:20px ;border: 1px solid black; background-color: whitesmoke; border-radius: 30px; margin: 20px 20px;\">\n");
            contents.append("<h1 style=\"color:white; background-color:coral ;margin-top:20px;\">안녕하세요 Ugos Dev blog에</h1>\n");
            contents.append("<h2 style=\"color:rgb(47, 255, 82); background:royalblue;\">요청하신 인증번호입니다.</h2>");
            contents.append("<small style=\"margin:20px 0;\">브라우저로 돌아가 아래 번호를 입력해 주세요.</small>");
            contents.append("<p style=\"  text-align:center; color:rgb(27, 41, 20);font-size:30px;  border-radius: 30px; border:2px solid rgb(47, 255, 82)\">"+verifyNum+"</p>");
            contents.append("<p style=\"margin:20px; font-family:monospace; font-weight: 700;\">UGO's Dev Blog </p>");
            contents.append("</div>");
        }else {
            contents.append("<div style=\"width: 50%; padding:20px ;border: 1px solid black; background-color: whitesmoke; border-radius: 30px; margin: 20px 20px;\">\n");
            contents.append("<h1 style=\"color:white; background-color:coral ;margin-top:20px;\">안녕하세요 Ugos Dev blog 입니다</h1>\n");
            contents.append("<h2 style=\"color:rgb(47, 255, 82); background:royalblue;\">회원님의 아이디는</h2>");
            contents.append("<p style=\" text-align:center; color:rgb(27, 41, 20);font-size:30px; border-radius: 30px; border:2px solid rgb(47, 255, 82)\">"+userName+" 입니다.</p>");
            contents.append("<p style=\"margin:20px; font-family:monospace; font-weight: 700;\">UGO's Dev Blog </p>");
            contents.append("</div>");
        }

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session mailSession = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(AdminEmail,AdminPwd);
                    }
                });

        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(fromMail, MimeUtility.encodeText(fromName,"UTF-8","B")));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toMail)
            );
            message.setSubject(subject);
            message.setContent(contents.toString(),"text/html;charset=UTF-8");
            message.setSentDate(new Date());

            Transport t = mailSession.getTransport("smtp");
            t.connect(AdminEmail,AdminPwd);
            t.sendMessage(message,message.getAllRecipients());
            t.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return verifyNum;
    }
```

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