package com.itamar.rest.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;


import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableScheduling
public class AppConfiguration {

    @Bean(name = "tokens")
    public Map<String,ClientSession> tokensMap() {
        return new HashMap<>();
    }

}
