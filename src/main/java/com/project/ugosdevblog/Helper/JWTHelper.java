package com.project.ugosdevblog.Helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.ugosdevblog.dto.TokenVerifyResult;
import com.project.ugosdevblog.entity.User;
import java.time.Instant;
import java.util.Date;


public class JWTHelper {
    private static final Algorithm ALGORITHM = Algorithm.HMAC256("ugosdevblog");
    private static final long AUTH_TIME = 60 * 1 * 3 ;
    private static final long REFRESH_TIME = 60 * 1 * 4;

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
}
