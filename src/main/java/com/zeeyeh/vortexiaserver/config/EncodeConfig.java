package com.zeeyeh.vortexiaserver.config;

import com.zeeyeh.vortexiaserver.provider.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncodeConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder();
    }
}
