package com.project.ugosdevblog.core.user.application;

import com.project.ugosdevblog.core.user.domain.*;
import com.project.ugosdevblog.common.support.S3ImageUploader;
import com.project.ugosdevblog.web.user.UserFormData;
import com.project.ugosdevblog.web.user.dto.UpdateUserReq;
import com.project.ugosdevblog.core.auth.UserAuthority;
import com.project.ugosdevblog.core.auth.AuthRepository;
import com.project.ugosdevblog.core.content.infra.MailCommand;
import com.project.ugosdevblog.core.content.infra.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;

    private final PasswordEncoder encoder;

    private final MailSender mailSender;

    private final S3ImageUploader imageUploader;

    @Value("${app.aws.sdk-host}")
    private  String sdkHost;


    public boolean CheckDuplication(String userId){
        Optional<User> byUserId = userRepository.findByUsername(userId);
        return byUserId.isPresent();
    }
    public void saveUser(UserFormData userFormData) throws IOException {
        MultipartFile profile = userFormData.getProfile();
        long timeStamp = System.currentTimeMillis();
        String imageKey = timeStamp+":profile:"+profile.getOriginalFilename();
        imageUploader.upload(profile.getInputStream(),imageKey,profile.getContentType(),profile.getSize());
        String profileUrl = sdkHost+imageKey;
        User user = User.builder()
                .email(userFormData.getEmail())
                .password(encoder.encode(userFormData.getPassword()))
                .emailSubscribe(false)
                .enabled(true)
                .profileUrl(profileUrl)
                .username(userFormData.getUserId())
                .signUpAt(LocalDateTime.now())
                .build();
        User savedUser = userRepository.save(user);

        if(userFormData.getUserId().equals("stau04")){
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

    public String updateUserInfo(UpdateUserReq reqData) throws IOException {

        MultipartFile profile = reqData.getProfile();
        long timeStamp = System.currentTimeMillis();

        String imageKey = timeStamp+":profile:"+profile.getOriginalFilename();
        String profileUrl = sdkHost+imageKey;
        String imageNameToDelete = reqData.getImageUrlBeforeUpdate().substring(62);

        //이전 이미지 삭제
        imageUploader.delete(imageNameToDelete);
        //새로운 이미지 저장
        imageUploader.upload(
                profile.getInputStream(),
                imageKey,
                profile.getContentType(),
                profile.getSize()

        );
        Optional<User> byUsername = userRepository.findByUsername(reqData.getUsername());
        User user = byUsername.orElseThrow(() -> new UsernameNotFoundException("유저없음"));
        user.setEmail(reqData.getEmail());
        user.setEmailSubscribe(reqData.isEmailSubscribe());
        user.setUpdateAt(LocalDateTime.now());
        user.setProfileUrl(profileUrl);
        return profileUrl;
    }

    public void findUserId(String email) {
        Optional<User> userByEmail = userRepository.findByEmail(email);
        User user = userByEmail.orElseThrow(UserNotFoundException::new);
        Map<String, String> messageProps = new HashMap<>();
        messageProps.put("username",user.getUsername());
        mailSender.sendMail(email, MailCommand.FIND,messageProps);
    }
    public int sendVerifyNum(String email,String username){
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(UserNotFoundException::new);
        Map<String, String> msgProps = new HashMap<>();
        return mailSender.sendMail(email, MailCommand.SIGNUP,msgProps);
    }


    public void changePwd(String username , String pwd) {
        Optional<User> byUsername = userRepository.findByUsername(username);
        User user = byUsername.orElseThrow(() -> new NotExistUserException("유저가 존재하지 않습니다"));
        user.setPassword(encoder.encode(pwd));
    }

    public Integer emailVerify(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if(byEmail.isPresent()){
            throw  new ExistUserException("해당 이메일로 이미 가입된 유저가 존재합니다.");
        }
        return mailSender.sendMail(email, MailCommand.SIGNUP,new HashMap<>());
    }
}
