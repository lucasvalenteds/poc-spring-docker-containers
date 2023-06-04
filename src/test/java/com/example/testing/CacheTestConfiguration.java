package com.example.testing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisConnectionDetails;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.testcontainers.service.connection.ServiceConnectionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
@ImportAutoConfiguration(ServiceConnectionAutoConfiguration.class)
public class CacheTestConfiguration {

    @Value("${spring.cache.redis.docker-image}")
    private String dockerImageName;

    @Bean
    @ServiceConnection(name = "redis", type = RedisConnectionDetails.class)
    GenericContainer<?> cacheContainer() {
        return new GenericContainer<>(DockerImageName.parse(dockerImageName))
                .withExposedPorts(6379);
    }
}
