package com.example;

import com.example.testing.ApplicationTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(ApplicationTestConfiguration.class)
class ApplicationTest {

    @Test
    void contextLoads() {
    }
}
