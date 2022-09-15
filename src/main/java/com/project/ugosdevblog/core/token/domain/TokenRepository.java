package com.project.ugosdevblog.core.token.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,String> {
    Optional<Token> findTokenByUsernameAndToken(String username, String token);

}
