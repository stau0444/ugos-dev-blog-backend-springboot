package com.project.ugosdevblog.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.project.ugosdevblog.Helper.JWTHelper;
import com.project.ugosdevblog.dto.LoginReq;
import com.project.ugosdevblog.dto.TokenVerifyResult;
import com.project.ugosdevblog.entity.Token;
import com.project.ugosdevblog.entity.User;
import com.project.ugosdevblog.service.TokenService;
import com.project.ugosdevblog.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final TokenService tokenService;

    public JWTLoginFilter(
            AuthenticationManager authenticationManager ,
                          UserService userService,
                          TokenService tokenService,
                          ObjectMapper objectMapper
    ){
        super(authenticationManager);
        this.userService = userService;
        this.tokenService = tokenService;
        this.objectMapper = objectMapper;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        LoginReq loginReq = null;
        String refresh_token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(refresh_token == null){
            try {
                loginReq = objectMapper.readValue(request.getInputStream(),LoginReq.class);
            }catch (IOException e){
                e.printStackTrace();
            }
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    loginReq.getUserId(),loginReq.getPassword()
            );
            return getAuthenticationManager().authenticate(token);
        }else{
            String refreshToken = refresh_token.substring("Bearer ".length());
            System.out.println("refreshToken = " + refreshToken);
            TokenVerifyResult result = JWTHelper.verify(refreshToken);
            //DB에서 토큰 체크
            tokenService.findToken(result.getUsername(),refreshToken).orElseThrow(
                    ()->{
                        tokenService.deleteAll(result.getUsername());
                        throw new TokenExpiredException("인증토큰이 탈취 됨");
                    }
            );
            if(result.isSuccess()){
                User user = (User) userService.loadUserByUsername(result.getUsername());
                return new UsernamePasswordAuthenticationToken(user,user.getAuthorities());
            }else{
                throw new TokenExpiredException("토큰이 만료되었습니다 다시 로그인 해주세요");
            }
        }


    }


    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String refreshToken = JWTHelper.createRefreshToken(user);
        System.out.println("리프레시 토큰 생성 ="+ refreshToken);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("auth_token", JWTHelper.createAuthToken(user));
        response.setHeader("refresh_token" , refreshToken);

        response.getOutputStream().write(objectMapper.writeValueAsBytes(user));
        //DB에 토큰 저장
        tokenService.saveToken(
                Token.builder()
                        .username(user.getUsername())
                        .token(refreshToken)
                        .build()
        );
    }
}
