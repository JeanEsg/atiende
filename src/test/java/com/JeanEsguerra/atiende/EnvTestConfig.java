package com.JeanEsguerra.atiende;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

public class EnvTestConfig
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        Dotenv dotenv = Dotenv.configure()
                .directory(".")
                .ignoreIfMissing()
                .load();

        Map<String, Object> properties = Map.of(
                "DB_URL", dotenv.get("DB_URL", ""),
                "DB_USERNAME", dotenv.get("DB_USERNAME", ""),
                "DB_PASSWORD", dotenv.get("DB_PASSWORD", "")
        );

        ConfigurableEnvironment environment = context.getEnvironment();
        environment.getPropertySources().addFirst(
                new MapPropertySource("dotenvTestProperties", properties)
        );
    }
}

