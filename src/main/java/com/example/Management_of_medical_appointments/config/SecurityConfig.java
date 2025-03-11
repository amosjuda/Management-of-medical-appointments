package com.example.Management_of_medical_appointments.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  //Temporarily disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/doctor").permitAll()  //Allows public access to this endpoint
                        .requestMatchers("/doctor/{id}").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
