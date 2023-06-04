package com.example;

import com.example.testing.ApplicationTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
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
}
