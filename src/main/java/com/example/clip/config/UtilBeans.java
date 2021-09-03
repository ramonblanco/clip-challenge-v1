package com.example.clip.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilBeans {

    @Bean
    Faker getFaker() {
        return new Faker();
    }
}
