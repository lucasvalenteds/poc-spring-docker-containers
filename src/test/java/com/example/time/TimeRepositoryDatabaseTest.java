package com.example.time;

import com.example.testing.DatabaseTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TimeRepositoryDatabaseTest.Config.class)
class TimeRepositoryDatabaseTest {

    @Autowired
    private TimeRepository timeRepository;

    @Test
    void gettingTimeFromDatabase() {
        final var instantBeforeQuery = Instant.now();

        final var instantAfterQuery = timeRepository.now();

        assertThat(instantAfterQuery).isAfterOrEqualTo(instantBeforeQuery);
    }

    @TestConfiguration
    @Import(DatabaseTestConfiguration.class)
    static class Config {

        @Bean
        TimeRepository timeRepository(JdbcTemplate jdbcTemplate) {
            return new TimeRepositoryDatabase(jdbcTemplate);
        }
    }
}
