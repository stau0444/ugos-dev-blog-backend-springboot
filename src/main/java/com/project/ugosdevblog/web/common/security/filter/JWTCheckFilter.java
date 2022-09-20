package com.project.ugosdevblog.web.common.security.filter;

import com.project.ugosdevblog.web.common.security.JWTHelper;
import org.springframework.http.HttpHeaders;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JWTCheckFilter extends BasicAuthenticationFilter {


    private final JWTHelper.TokenVerifier tokenVerifier;


    public JWTCheckFilter(
            AuthenticationManager authenticationManager,
            JWTHelper.TokenVerifier tokenVerifier
    ){
        super(authenticationManager);
        this.tokenVerifier = tokenVerifier;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
            String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            String refreshToken = request.getHeader("refresh_token");

          // 토큰이 없거나 , JWT 형식의 토큰이 아닐 경우
            if((authToken == null && refreshToken == null) || !authToken.startsWith("Bearer")){
                chain.doFilter(request,response);
                return;
            }

            tokenVerifier.verify(authToken,refreshToken,request,response,chain);

    }
}
