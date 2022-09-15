package com.project.ugosdevblog.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ugosdevblog.web.security.CustomAuthEntryPoint;
import com.project.ugosdevblog.web.security.CustomExceptionHandler;
import com.project.ugosdevblog.web.security.JWTHelper;
import com.project.ugosdevblog.web.security.filter.JWTCheckFilter;
import com.project.ugosdevblog.web.security.filter.JWTLoginFilter;
import com.project.ugosdevblog.core.application.TokenService;
import com.project.ugosdevblog.core.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final TokenService tokenService;
    private final CustomExceptionHandler exceptionHandler;

    private final JWTHelper.TokenVerifier tokenVerifier;

    @Bean
    public CustomAuthEntryPoint authenticationEntryPoint() {
        return new CustomAuthEntryPoint();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JWTCheckFilter checkFilter = new JWTCheckFilter(authenticationManager(),tokenVerifier);
        JWTLoginFilter loginFilter = new JWTLoginFilter(authenticationManager(),userService,tokenService,objectMapper,exceptionHandler);

        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS,"/api/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/api/content").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.POST,"/api/content").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.GET,"/api/user/test").authenticated()
                .anyRequest().permitAll().and()
                .csrf().disable()
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilterAt(loginFilter , UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(checkFilter , BasicAuthenticationFilter.class)
                ;

    }



    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
