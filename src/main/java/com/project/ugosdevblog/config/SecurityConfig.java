package com.project.ugosdevblog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ugosdevblog.filter.JWTCheckFilter;
import com.project.ugosdevblog.filter.JWTLoginFilter;
import com.project.ugosdevblog.repository.TokenRepository;
import com.project.ugosdevblog.service.TokenService;
import com.project.ugosdevblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
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
    private final CustomAuthEntryPoint authEntryPoint;

    @Bean
    public CustomAuthEntryPoint authenticationEntryPoint() {
        return new CustomAuthEntryPoint();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JWTCheckFilter checkFilter = new JWTCheckFilter(authenticationManager(),userService,authEntryPoint,tokenService,objectMapper);
        JWTLoginFilter loginFilter = new JWTLoginFilter(authenticationManager(),userService,tokenService,objectMapper);

        http.
                csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/user/test").authenticated()
                .anyRequest().permitAll()
                .and()
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
