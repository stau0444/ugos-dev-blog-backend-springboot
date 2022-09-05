package com.project.ugosdevblog.filter;

import com.project.ugosdevblog.Helper.JWTHelper;
import com.project.ugosdevblog.dto.TokenVerifyResult;
import com.project.ugosdevblog.entity.User;
import com.project.ugosdevblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenVerifier {

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
