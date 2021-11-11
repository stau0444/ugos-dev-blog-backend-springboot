package com.project.ugosdevblog.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ugosdevblog.Helper.JWTHelper;
import com.project.ugosdevblog.dto.TokenExpiredResp;
import com.project.ugosdevblog.dto.TokenVerifyResult;
import com.project.ugosdevblog.entity.User;
import com.project.ugosdevblog.exception.NotValidTokenException;
import com.project.ugosdevblog.service.UserService;

import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JWTCheckFilter extends BasicAuthenticationFilter {

    private final UserService userService;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public JWTCheckFilter(
            AuthenticationManager authenticationManager,
            UserService userService,
            AuthenticationEntryPoint authenticationEntryPoint
    ){
        super(authenticationManager);
        this.userService = userService;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try{
                String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
                if(bearer == null || !bearer.startsWith("Bearer")){
                    chain.doFilter(request,response);
                    return;
                }

                String token = bearer.substring("Bearer ".length());
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
                    throw new NotValidTokenException("토큰 만료됨 로그인을 다시해주세요");
                }
        }catch (AuthenticationException exception){
            if(exception instanceof NotValidTokenException){

            }
            SecurityContextHolder.clearContext();
            onUnsuccessfulAuthentication(request, response, exception);
            this.authenticationEntryPoint.commence(request, response, exception);
            return;
        }
    }
}
