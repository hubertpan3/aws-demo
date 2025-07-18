package com.icarusfrog.aws_demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers("/login", "/error" ,"/api/**", "/actuator/**").permitAll()
                        .anyRequest().authenticated()
        ).csrf(
                csrf -> csrf.ignoringRequestMatchers("/api/**", "/actuator/**")
        ).formLogin(Customizer.withDefaults()
        ).logout(
                Customizer.withDefaults()
        );
        return http.build();
    }
}
