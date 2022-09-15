package com.project.ugosdevblog.core.token.application;

import com.project.ugosdevblog.core.token.domain.Token;
import com.project.ugosdevblog.core.token.domain.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public String deleteAll(String username) {
        try {
            repository.deleteById(username);
        } catch (EmptyResultDataAccessException e) {
            return e.getMessage();
        }
        return "";
    }
}
