package com.project.ugosdevblog.web.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.ugosdevblog.core.user.application.UserService;
import com.project.ugosdevblog.web.dto.token.TokenVerifyResult;
import com.project.ugosdevblog.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;


public class JWTHelper {
    private static final Algorithm ALGORITHM = Algorithm.HMAC256("ugosdevblog");
    private static final long AUTH_TIME = 60 * 10;
    private static final long REFRESH_TIME = 60 * 60;

    public static String createAuthToken(User user){
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("exp" , Instant.now().getEpochSecond() + AUTH_TIME)
                .sign(ALGORITHM);
    }

    public static String createRefreshToken(User user){
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("exp" , Instant.now().getEpochSecond()+ REFRESH_TIME)
                .sign(ALGORITHM);
    }

    public static Date getExpiresDate(String token){
        return JWT.require(ALGORITHM).build().verify(token).getExpiresAt();
    }
    public static TokenVerifyResult verify(String token){
        try{
            DecodedJWT result = JWT.require(ALGORITHM).build().verify(token);
            return TokenVerifyResult.builder()
                        .success(true)
                        .username(result.getSubject())
                        .build();

        }catch (Exception e){
            DecodedJWT decode = JWT.decode(token);
            return TokenVerifyResult.builder()
                    .success(false)
                    .username(decode.getSubject())
                    .build();
        }
    }

    @Component
    @RequiredArgsConstructor
    public static class TokenVerifier {

        private final UserService userService;

        public void verify(
                String authToken ,
                String refreshToken ,
                HttpServletRequest request ,
                HttpServletResponse response ,
                FilterChain chain
        ) throws ServletException, IOException {

            String token = authToken.substring("Bearer ".length());
            TokenVerifyResult result = JWTHelper.verify(token);

            if(result.isSuccess()){
                User user = (User) userService.loadUserByUsername(result.getUsername());

                UsernamePasswordAuthenticationToken verifiedToken = new UsernamePasswordAuthenticationToken(
                        user.getUsername(),null,user.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(verifiedToken);
                chain.doFilter(request,response);
            }else{
                //no Authorize
                response.sendError(401,"ACCESS_TOKEN_EXPIRED");
            }
        }
    }
}
