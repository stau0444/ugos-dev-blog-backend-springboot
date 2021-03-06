package com.project.ugosdevblog.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ugosdevblog.Helper.JWTHelper;
import com.project.ugosdevblog.config.CustomExceptionHandler;
import com.project.ugosdevblog.dto.LoginReq;
import com.project.ugosdevblog.dto.LoginStateResp;
import com.project.ugosdevblog.dto.LoginUserInfo;
import com.project.ugosdevblog.dto.TokenVerifyResult;
import com.project.ugosdevblog.entity.Token;
import com.project.ugosdevblog.entity.User;
import com.project.ugosdevblog.exception.NotValidTokenException;
import com.project.ugosdevblog.service.TokenService;
import com.project.ugosdevblog.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
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


        //????????? ?????? ?????? ?????????
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
        //???????????? ????????? ?????? ?????????
            String refreshToken = refresh_token.substring("Bearer ".length());
            TokenVerifyResult result = JWTHelper.verify(refreshToken);
            tokenService.findToken(result.getUsername(),refreshToken).orElseThrow(
                    ()->{

                        try{
                            tokenService.deleteAll(result.getUsername());
                        }catch (UnexpectedRollbackException e){
                            System.out.println("token error " + e);
                        }
                        throw new TokenExpiredException("??????????????? ?????? ?????? ????????? ???????????????????????????. ??????????????? ??????????????????");
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
                if(request.getMethod().equals("OPTIONS")){
                    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*");
                    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"*");
                    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"*");
                    response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,"*");
                    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,"true");
                }else{
                    System.out.println("OTHERS REQUEST"+request.getMethod());
                    User user = (User) authResult.getPrincipal();
                    String refreshToken = JWTHelper.createRefreshToken(user);

                    response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    response.setHeader("auth_token", JWTHelper.createAuthToken(user));
                    response.setHeader("refresh_token" , refreshToken);
                    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*");
                    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"*");
                    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"*");
                    response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,"*");
                    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,"true");

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
                    //DB??? ?????? ??????
                    tokenService.saveToken(
                            Token.builder()
                                    .username(user.getUsername())
                                    .token(refreshToken)
                                    .build()
                    );
                }
            }
}
