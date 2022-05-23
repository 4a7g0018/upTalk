package com.UPT.uptalk_spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @Title: TestConfiguration
 * @author: Benson-Yan
 * @version: 1.0.0
 * @time: 2022/5/18
 */

@Configuration
public class BasicConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
