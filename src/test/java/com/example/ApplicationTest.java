package com.example;

import com.example.testing.ApplicationTestConfiguration;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(ApplicationTestConfiguration.class)
class ApplicationTest {

    @Autowired
    private Environment environment;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void databaseConfigurationIsWorking() {
        assertThat(environment.containsProperty("spring.datasource.docker-image")).isTrue();
        assertThat(applicationContext.getBeansOfType(DataSource.class)).isNotEmpty();
        assertThat(applicationContext.getBeansOfType(JdbcTemplate.class)).isNotEmpty();
    }

    @Test
    void cacheConfigurationIsWorking() {
        assertThat(environment.containsProperty("spring.cache.redis.docker-image")).isTrue();
        assertThat(applicationContext.getBeansOfType(RedisTemplate.class)).isNotEmpty();
    }

    @Test
    void databaseMigrationIsWorking() {
        assertThat(applicationContext.getBeansOfType(Flyway.class)).isNotEmpty();

        final var flyway = applicationContext.getBean(Flyway.class);
        final var migrationInfo = flyway.info();

        assertThat(migrationInfo.all()).hasSize(1);
        assertThat(migrationInfo.applied()).hasSize(1);
        assertThat(migrationInfo.pending()).isEmpty();
    }
}
