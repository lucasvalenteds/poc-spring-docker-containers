package com.example.testing;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({DatabaseTestConfiguration.class, CacheTestConfiguration.class})
public class ApplicationTestConfiguration {
}
