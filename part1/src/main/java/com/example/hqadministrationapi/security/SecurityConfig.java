package com.example.hqadministrationapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // HR can perform all operations; Manager is read-only (GET only).
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // HR: full access (create / update / delete / promote)
                        .requestMatchers(HttpMethod.POST,   "/api/**").hasRole("HR")
                        .requestMatchers(HttpMethod.PUT,    "/api/**").hasRole("HR")
                        .requestMatchers(HttpMethod.PATCH,  "/api/**").hasRole("HR")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("HR")
                        // MANAGER: read-only (HR can also read)
                        .requestMatchers(HttpMethod.GET,    "/api/**").hasAnyRole("HR", "MANAGER")
                        // Everything else: must be authenticated
                        .anyRequest().authenticated()
                )
                .httpBasic(b -> {});

        return http.build();
    }
}