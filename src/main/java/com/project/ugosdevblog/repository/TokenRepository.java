package com.project.ugosdevblog.repository;

import com.project.ugosdevblog.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,String> {
    Optional<Token> findTokenByUsernameAndToken(String username, String token);
}
