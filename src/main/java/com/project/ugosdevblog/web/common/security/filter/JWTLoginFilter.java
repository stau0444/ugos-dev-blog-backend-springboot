package com.project.ugosdevblog.web.common.security.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ugosdevblog.web.common.security.JWTHelper;
import com.project.ugosdevblog.web.common.security.CustomExceptionHandler;
import com.project.ugosdevblog.web.user.dto.LoginReq;
import com.project.ugosdevblog.web.user.dto.LoginStateResp;
import com.project.ugosdevblog.web.user.dto.LoginUserInfo;
import com.project.ugosdevblog.web.token.dto.TokenVerifyResult;
import com.project.ugosdevblog.core.token.domain.Token;
import com.project.ugosdevblog.core.user.domain.User;
import com.project.ugosdevblog.web.common.security.NotValidTokenException;
import com.project.ugosdevblog.core.token.application.TokenService;
import com.project.ugosdevblog.core.user.application.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.UnexpectedRollbackException;


import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final TokenService tokenService;

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    public JWTLoginFilter(
            AuthenticationManager authenticationManager ,
                          UserService userService,
                          TokenService tokenService,
                          ObjectMapper objectMapper,
                          CustomExceptionHandler exceptionHandler
    ){
        super(authenticationManager);
        this.userService = userService;
        this.tokenService = tokenService;
        this.objectMapper = objectMapper;
        setFilterProcessesUrl("/api/user/login");
        setAuthenticationFailureHandler(exceptionHandler);
    }


    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        LoginReq loginReq = null;
        String refresh_token = request.getHeader(HttpHeaders.AUTHORIZATION);


        //로그인 폼을 통한 로그인
        if(refresh_token == null){
            try {
                if(request.getMethod().equals("OPTIONS")){
                    return new UsernamePasswordAuthenticationToken("","");
                }
                loginReq = objectMapper.readValue(request.getInputStream(),LoginReq.class);

            }catch (IOException e){
                e.printStackTrace();
            }

            UsernamePasswordAuthenticationToken token  = new UsernamePasswordAuthenticationToken(loginReq.getUserId(),loginReq.getPassword());

            return getAuthenticationManager().authenticate(token);
        }else{
        //리프레쉬 토큰을 통한 로그인
            String refreshToken = refresh_token.substring("Bearer ".length());
            TokenVerifyResult result = JWTHelper.verify(refreshToken);
            tokenService.findToken(result.getUsername(),refreshToken).orElseThrow(
                    ()->{
                        try{
                            tokenService.deleteAll(result.getUsername());
                        }catch (UnexpectedRollbackException e){
                            logger.debug(e.getMessage());
                        }
                        throw new TokenExpiredException("인증토큰이 탈취 되어 강제로 로그아웃되었습니다. 비밀번호를 변경해주세요");
                    }
            );
            if(result.isSuccess()){
                User user = (User) userService.loadUserByUsername(result.getUsername());
                return new UsernamePasswordAuthenticationToken(user,user.getAuthorities());
            }else{
                throw new NotValidTokenException("REFRESH_TOKEN_EXPIRED");
            }
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException{
                User user = (User) authResult.getPrincipal();
                String refreshToken = JWTHelper.createRefreshToken(user);

                response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                response.setHeader("auth_token", JWTHelper.createAuthToken(user));
                response.setHeader("refresh_token" , refreshToken);


                response.getOutputStream().write(objectMapper.writeValueAsBytes(
                        LoginStateResp.builder()
                                .isLogin(true)
                                .userInfo(LoginUserInfo.builder()
                                        .email(user.getEmail())
                                        .id(user.getId())
                                        .profileUrl(user.getProfileUrl())
                                        .emailSubscribe(user.isEmailSubscribe())
                                        .username(user.getUsername())
                                        .signUpAt(DateTimeFormatter.ISO_LOCAL_DATE.format(user.getSignUpAt()))
                                        .build())
                                .build()
                ));
                //DB에 토큰 저장
                tokenService.saveToken(
                        Token.builder()
                                .username(user.getUsername())
                                .token(refreshToken)
                                .build()
                );
        }
}
