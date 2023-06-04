package com.example.time;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Instant;

@WebMvcTest(controllers = TimeRestController.class)
@AutoConfigureWebTestClient
class TimeRestControllerTest {

    @MockBean(name = "timeRepositoryDatabase")
    private TimeRepository timeRepositoryDatabase;

    @MockBean(name = "timeRepositoryCache")
    private TimeRepository timeRepositoryCache;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void gettingTimeFromDatabase() {
        final var timestamp = "2023-06-04T03:28:12.431278Z";
        Mockito.when(timeRepositoryDatabase.now())
                .thenReturn(Instant.parse(timestamp));

        webTestClient.get()
                .uri("/time/database")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.timestamp").value(Matchers.equalTo(timestamp));

        Mockito.verify(timeRepositoryDatabase, Mockito.times(1))
                .now();
        Mockito.verifyNoInteractions(timeRepositoryCache);
    }

    @Test
    void gettingTimeFromCache() {
        final var timestamp = "2023-06-04T03:28:18.439Z";
        Mockito.when(timeRepositoryCache.now())
                .thenReturn(Instant.parse(timestamp));

        webTestClient.get()
                .uri("/time/cache")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.timestamp").value(Matchers.equalTo(timestamp));

        Mockito.verify(timeRepositoryCache, Mockito.times(1))
                .now();
        Mockito.verifyNoInteractions(timeRepositoryDatabase);
    }
}
