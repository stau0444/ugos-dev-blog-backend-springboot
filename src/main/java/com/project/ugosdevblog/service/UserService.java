package com.project.ugosdevblog.service;

import com.project.ugosdevblog.dto.LoginReq;
import com.project.ugosdevblog.dto.UserPostReq;
import com.project.ugosdevblog.entity.User;
import com.project.ugosdevblog.entity.UserAuthority;
import com.project.ugosdevblog.repository.AuthRepository;
import com.project.ugosdevblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;

    public boolean CheckDuplication(String userId){
        Optional<User> byUserId = userRepository.findByUsername(userId);
        boolean isExistId = byUserId.isPresent();
        if(isExistId){
            return true;
        }
        return false;
    }
    public void saveUser(User user){

        User savedUser = userRepository.save(user);
        UserAuthority authority = UserAuthority.builder()
                .id(savedUser.getId())
                .authority("ROLE_USER")
                .build();
        UserAuthority auth = authRepository.save(authority);
        savedUser.setAuthorities(List.of(auth));

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () ->new UsernameNotFoundException(username)
        );
    }
}
