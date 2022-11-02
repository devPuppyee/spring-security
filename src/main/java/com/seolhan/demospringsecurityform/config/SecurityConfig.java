package com.seolhan.demospringsecurityform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                // 루트와 info 페이지는 모두 접근 가능
                .mvcMatchers("/", "/info", "/account/**").permitAll()
                // admin 페이지는 ADMIN 권한만 접근 가능
                .mvcMatchers("/admin").hasRole("ADMIN")
                // 기타 등등은 인증만 하면 접근 가능
                .anyRequest().authenticated();
        // 폼 로그인
        http.formLogin();
        // basic authentication
        http.httpBasic();
        return http.build();
    }
}
