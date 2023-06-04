package com.example.time;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository("timeRepositoryDatabase")
public class TimeRepositoryDatabase implements TimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public TimeRepositoryDatabase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Instant now() {
        return jdbcTemplate.queryForObject("select now()", Instant.class);
    }
}
