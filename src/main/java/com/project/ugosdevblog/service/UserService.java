package com.project.ugosdevblog.service;

import com.project.ugosdevblog.dto.UpdateUserReq;
import com.project.ugosdevblog.entity.User;
import com.project.ugosdevblog.entity.UserAuthority;
import com.project.ugosdevblog.exception.NotExistUserException;
import com.project.ugosdevblog.repository.AuthRepository;
import com.project.ugosdevblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;

    public boolean CheckDuplication(String userId){
        Optional<User> byUserId = userRepository.findByUsername(userId);
        boolean isExistId = byUserId.isPresent();
        return isExistId;
    }
    public void saveUser(User user){
        User savedUser = userRepository.save(user);
        if(user.getUsername().equals("stau04")){
            UserAuthority userAuth = new UserAuthority(savedUser.getId(),"ROLE_USER");
            UserAuthority adminAuth = new UserAuthority(savedUser.getId(),"ROLE_ADMIN");
            savedUser.setAuthorities(Set.of(userAuth,adminAuth));
        }else{
            UserAuthority authority = new UserAuthority(savedUser.getId(),"ROLE_USER");
            UserAuthority userAuth = authRepository.save(authority);
            savedUser.setAuthorities(Set.of(userAuth));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () ->new UsernameNotFoundException(username)
        );
    }

    public void updateUserInfo(UpdateUserReq reqData) {
        Optional<User> byUsername = userRepository.findByUsername(reqData.getUsername());
        User user = byUsername.orElseThrow(() -> new UsernameNotFoundException("유저없음"));
        user.setEmail(reqData.getEmail());
        user.setEmailSubscribe(reqData.isEmailSubscribe());
        user.setUpdateAt(LocalDateTime.now());
        user.setProfileUrl(reqData.getProfileUrl());
    }

    public void findUserId(String email) {
        Optional<User> userByEmail = userRepository.findByEmail(email);
        User user = userByEmail.orElseThrow(() -> new NotExistUserException("존재하지 않는 유저입니다."));
        sendMailToUser("stau04@gmail.com",user.getUsername());
    }
    public int sendVerifyNum(String email,String username){
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(()->new NotExistUserException("유저가 존재하지 않습니다"));
        return sendMailToUser(email, username);
    }

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
            contents.append("<h1 style={{color:'white',background:'lightgray',marginTop:'20px'}}>Ugo's Dev blog에 </h1>\n");
            contents.append("<h2 style={{color:'white',background:'royalblue'}}>요청하신 인증번호입니다.</h2>\n");
            contents.append("<p style={{margin:'20px 0'}}>브라우저로 돌아가 아래 번호를 입력해 주세요</p>\n");
            contents.append("<h3 style={{margin:'20px 0'}}>"+verifyNum+"</h3>\n");
            contents.append("<small style={{margin:'20px 0'}}>Ugo's Dev Blog </small>\n");
        }else {
            contents.append("<h1 style={{color:'white',background:'lightgray',marginTop:'20px'}}>Ugo's Dev blog에 </h1>\n");
            contents.append("<h2 style={{color:'white',background:'royalblue'}}>요청하신 인증번호입니다.</h2>\n");
            contents.append("<p style={{margin:'20px 0'}}>회원님의 아이디는</p>\n");
            contents.append("<h3 style={{margin:'20px 0'}}>"+userName+"  입니다</h3>\n");
            contents.append("<small style={{margin:'20px 0'}}>Ugo's Dev Blog </small>\n");
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

    public void changePwd(String username , String pwd ,PasswordEncoder encoder) {
        Optional<User> byUsername = userRepository.findByUsername(username);
        User user = byUsername.orElseThrow(() -> new NotExistUserException("유저가 존재하지 않습니다"));
        user.setPassword(encoder.encode(pwd));
    }
}
