package com.example.aimobilebackend.infrastructure.config;

import com.example.aimobilebackend.domain.repository.UserRepository;
import com.example.aimobilebackend.domain.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }
}