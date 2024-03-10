package com.example.lab1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers(HttpMethod.GET, "/hotel").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/hotel").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/hotel").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/hotel").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return http.build();
    }
}
