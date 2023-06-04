package com.example.time;

import java.time.Instant;

public record TimeResponse(String timestamp) {

    TimeResponse(Instant instant) {
        this(String.valueOf(instant));
    }
}
