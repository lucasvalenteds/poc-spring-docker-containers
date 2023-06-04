package com.example.time;

import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository("timeRepositoryCache")
public class TimeRepositoryCache implements TimeRepository {

    private final StringRedisTemplate redisTemplate;

    public TimeRepositoryCache(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Instant now() {
        return redisTemplate.execute((RedisCallback<Instant>) connection ->
                Instant.ofEpochMilli(connection.serverCommands().time()));
    }
}
