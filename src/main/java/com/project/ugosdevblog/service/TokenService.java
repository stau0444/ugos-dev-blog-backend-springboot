package com.project.ugosdevblog.service;

import com.project.ugosdevblog.entity.Token;
import com.project.ugosdevblog.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository repository;

    public void saveToken(Token token){
        repository.save(token);
    }
    public Optional<Token> findToken(String username,String refreshToken){
        return repository.findTokenByUsernameAndToken(username,refreshToken);
    }
    public void deleteAll(String username){
        repository.deleteById(username);
    }
}
