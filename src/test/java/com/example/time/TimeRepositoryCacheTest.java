package com.example.time;

import com.example.testing.ApplicationTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DataRedisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TimeRepositoryCacheTest.Config.class)
class TimeRepositoryCacheTest {

    @Autowired
    private TimeRepository timeRepository;

    @Test
    void gettingTimeFromCache() {
        final var instantBeforeCommand = Instant.now();

        final var instantAfterCommand = timeRepository.now();

        assertThat(instantAfterCommand).isAfterOrEqualTo(instantBeforeCommand);
    }

    @TestConfiguration
    @Import(ApplicationTestConfiguration.class)
    static class Config {

        @Bean
        TimeRepository timeRepository(StringRedisTemplate redisTemplate) {
            return new TimeRepositoryCache(redisTemplate);
        }
    }
}
