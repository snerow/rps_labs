package com.example.thymeleafdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Public pages
                .requestMatchers("/", "/home", "/login", "/signup", "/css/**", "/js/**").permitAll()

                // Manager endpoints
                .requestMatchers("/teachers/new", "/teachers/*/edit").hasRole("MANAGER")
                .requestMatchers("/students/new").hasAnyRole("MANAGER", "TEACHER")
                .requestMatchers("/students/*/edit").hasRole("MANAGER")
                .requestMatchers("/students/*/marks/edit").hasAnyRole("MANAGER", "TEACHER")

                // Teacher endpoints
                .requestMatchers("/subjects/new").hasRole("TEACHER")
                .requestMatchers("/tests/new").hasRole("TEACHER")

                // Student endpoints
                .requestMatchers("/tests/*/take").hasRole("STUDENT")
                .requestMatchers("/students/*/marks").hasAnyRole("STUDENT", "TEACHER", "MANAGER")

                // All other requests require authentication
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            )
            .rememberMe(remember -> remember
                .key("uniqueAndSecretKey")
                .tokenValiditySeconds(86400)
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
