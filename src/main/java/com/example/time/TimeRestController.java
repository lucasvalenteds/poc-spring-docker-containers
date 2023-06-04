package com.example.time;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/time")
public class TimeRestController {

    private final TimeRepository timeRepositoryDatabase;
    private final TimeRepository timeRepositoryCache;

    public TimeRestController(@Qualifier("timeRepositoryDatabase") TimeRepository timeRepositoryDatabase,
                              @Qualifier("timeRepositoryCache") TimeRepository timeRepositoryCache) {
        this.timeRepositoryDatabase = timeRepositoryDatabase;
        this.timeRepositoryCache = timeRepositoryCache;
    }

    @GetMapping("/database")
    public ResponseEntity<TimeResponse> getTimeFromDatabase() {
        return respondTimeFromRepository(timeRepositoryDatabase);
    }

    @GetMapping("/cache")
    public ResponseEntity<TimeResponse> getTimeFromCache() {
        return respondTimeFromRepository(timeRepositoryCache);
    }

    private static ResponseEntity<TimeResponse> respondTimeFromRepository(final TimeRepository timeRepository) {
        final var instant = timeRepository.now();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new TimeResponse(instant));
    }
}
