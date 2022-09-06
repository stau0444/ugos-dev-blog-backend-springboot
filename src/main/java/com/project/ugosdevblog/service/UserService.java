package com.project.ugosdevblog.service;

import com.project.ugosdevblog.dto.UpdateUserReq;
import com.project.ugosdevblog.entity.User;
import com.project.ugosdevblog.entity.UserAuthority;
import com.project.ugosdevblog.exception.ExistUserException;
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

    private final MailSender mailSender;

    public boolean CheckDuplication(String userId){
        Optional<User> byUserId = userRepository.findByUsername(userId);
        return byUserId.isPresent();
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
        sendMailToUser(email,user.getUsername());
    }
    public int sendVerifyNum(String email,String username){
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(()->new NotExistUserException("유저가 존재하지 않습니다"));
        return sendMailToUser(email, "nouser");
    }

    public int sendMailToUser(String userMail,String userName){
        return mailSender.sendMail(userMail,userName);
    }

    public void changePwd(String username , String pwd ,PasswordEncoder encoder) {
        Optional<User> byUsername = userRepository.findByUsername(username);
        User user = byUsername.orElseThrow(() -> new NotExistUserException("유저가 존재하지 않습니다"));
        user.setPassword(encoder.encode(pwd));
    }

    public Integer emailVerify(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if(byEmail.isPresent()){
            throw  new ExistUserException("해당 이메일로 이미 가입된 유저가 존재합니다.");
        }
        return sendMailToUser(email, "nouser");
    }
}
