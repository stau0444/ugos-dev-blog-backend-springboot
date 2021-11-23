package com.project.ugosdevblog.service;

import com.project.ugosdevblog.dto.UpdateUserReq;
import com.project.ugosdevblog.entity.User;
import com.project.ugosdevblog.entity.UserAuthority;
import com.project.ugosdevblog.repository.AuthRepository;
import com.project.ugosdevblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
}
