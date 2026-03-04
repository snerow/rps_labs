package com.example.thymeleafdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        // Manager user
        UserDetails manager = User.builder()
                .username("manager")
                .password(passwordEncoder.encode("manager"))
                .roles("MANAGER")
                .build();

        // Teacher user
        UserDetails teacher = User.builder()
                .username("teacher")
                .password(passwordEncoder.encode("teacher"))
                .roles("TEACHER")
                .build();

        // Student user
        UserDetails student = User.builder()
                .username("student")
                .password(passwordEncoder.encode("student"))
                .roles("STUDENT")
                .build();

        return new InMemoryUserDetailsManager(manager, teacher, student);
    }
}
