package com.example.testing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.testcontainers.service.connection.ServiceConnectionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
@ImportAutoConfiguration(ServiceConnectionAutoConfiguration.class)
public class DatabaseTestConfiguration {

    @Value("${spring.datasource.docker-image}")
    private String dockerImageName;

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> databaseContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse(dockerImageName));
    }
}
