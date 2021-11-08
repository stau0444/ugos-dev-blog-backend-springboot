package com.project.ugosdevblog.filter;

import com.project.ugosdevblog.Helper.JWTHelper;
import com.project.ugosdevblog.dto.TokenVerifyResult;
import com.project.ugosdevblog.entity.User;
import com.project.ugosdevblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTCheckFilter extends BasicAuthenticationFilter {

    private final UserService userService;

    public JWTCheckFilter(AuthenticationManager authenticationManager , UserService userService){
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(bearer == null || !bearer.startsWith("Bearer")){
            chain.doFilter(request,response);
            return;
        }

        String token = bearer.substring("Bearer".length());
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
            throw new AuthenticationException("Token is not valid");
        }
    }
}
