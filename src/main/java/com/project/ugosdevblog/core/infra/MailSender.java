package com.project.ugosdevblog.core.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class MailSender {

    private  final MailProperties mailProperties;


    public int sendMail(String userMail, MailCommand command , Map<String,String> msgProps){
        Random random = new Random();

        int verifyNum = random.nextInt(99999);

        StringBuilder contents = new StringBuilder();
        if(command == MailCommand.SIGNUP){
            contents.append("<div style=\"width: 50%; padding:20px ;border: 1px solid black; background-color: whitesmoke; border-radius: 30px; margin: 20px 20px;\">\n");
            contents.append("<h1 style=\"color:white; background-color:coral ;margin-top:20px;\">안녕하세요 Ugos Dev blog에</h1>\n");
            contents.append("<h2 style=\"color:rgb(47, 255, 82); background:royalblue;\">요청하신 인증번호입니다.</h2>");
            contents.append("<small style=\"margin:20px 0;\">브라우저로 돌아가 아래 번호를 입력해 주세요.</small>");
            contents.append("<p style=\"  text-align:center; color:rgb(27, 41, 20);font-size:30px;  border-radius: 30px; border:2px solid rgb(47, 255, 82)\">").append(verifyNum).append("</p>");
            contents.append("<p style=\"margin:20px; font-family:monospace; font-weight: 700;\">UGO's Dev Blog </p>");
            contents.append("</div>");
        }else if(command == MailCommand.FIND){
            contents.append("<div style=\"width: 50%; padding:20px ;border: 1px solid black; background-color: whitesmoke; border-radius: 30px; margin: 20px 20px;\">\n");
            contents.append("<h1 style=\"color:white; background-color:coral ;margin-top:20px;\">안녕하세요 Ugos Dev blog 입니다</h1>\n");
            contents.append("<h2 style=\"color:rgb(47, 255, 82); background:royalblue;\">회원님의 아이디는</h2>");
            contents.append("<p style=\" text-align:center; color:rgb(27, 41, 20);font-size:30px; border-radius: 30px; border:2px solid rgb(47, 255, 82)\">").append(msgProps.get("username")).append(" 입니다.</p>");
            contents.append("<p style=\"margin:20px; font-family:monospace; font-weight: 700;\">UGO's Dev Blog </p>");
            contents.append("</div>");
        }else {
            contents.append("<div style=\"width: 50%; padding:20px ;border: 1px solid black; background-color: whitesmoke; border-radius: 30px; margin: 20px 20px;\">\n");
            contents.append("<h1 style=\"color:white; background-color:coral; padding:0 10px; margin-top:20px;\"><span style=\"color:lightgray\">")
                    .append(msgProps.get("username"))
                    .append("</span>")
                    .append("님 안녕하세요 <span style=\"color:lightgray\">Ugos Dev blog </span>입니다</h1>\n");
            contents.append("<h2 style=\"color:rgb(47, 255, 82); padding:0 10px;  background:royalblue;\"> 새로운 컨텐츠가 추가되었습니다!</h2>");
            contents.append("<a style=\" text-align:center; color:rgb(27, 41, 20);font-size:15px; border-radius: 30px; \" href=\"https://blog.ugosdev.com/content/")
                    .append(msgProps.get("contentId"))
                    .append("\"> 제목: ")
                    .append(msgProps.get("contentTitle"))
                    .append("</a>");
            contents.append("<p style=\" color:gray; margin:10px; font-family:monospace;\">링크를 클릭하면 해당 글로 이동합니다. </p>");
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
                        return new PasswordAuthentication(mailProperties.getAdminEmail(),mailProperties.getAdminPwd());
                    }
                });

        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(mailProperties.getFromMail(), MimeUtility.encodeText(mailProperties.getFromMail(),"UTF-8","B")));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(userMail)
            );
            message.setSubject(mailProperties.getSubject());
            message.setContent(contents.toString(),"text/html;charset=UTF-8");
            message.setSentDate(new Date());

            Transport t = mailSession.getTransport("smtp");
            t.connect(mailProperties.getAdminEmail(),mailProperties.getAdminPwd());
            t.sendMessage(message,message.getAllRecipients());
            t.close();
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return verifyNum;
    }

}
