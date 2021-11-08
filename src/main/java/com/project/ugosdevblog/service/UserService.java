package com.project.ugosdevblog.service;

import com.project.ugosdevblog.dto.LoginReq;
import com.project.ugosdevblog.dto.UserPostReq;
import com.project.ugosdevblog.entity.User;
import com.project.ugosdevblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public boolean CheckDuplication(String userId){
        Optional<User> byUserId = userRepository.findByUsername(userId);
        boolean isExistId = byUserId.isPresent();
        if(isExistId){
            return true;
        }
        return false;
    }

    public void saveUser(UserPostReq reqData){
        User user = User.builder()
                .email(reqData.getEmail())
                .password(encoder.encode(reqData.getPassword()))
                .emailSubscribe(false)
                .username(reqData.getUserId())
                .signUpAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () ->new UsernameNotFoundException(username)
        );
    }
}
