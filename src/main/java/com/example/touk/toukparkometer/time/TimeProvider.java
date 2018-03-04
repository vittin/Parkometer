package com.example.touk.toukparkometer.time;

import java.time.LocalDateTime;

public interface TimeProvider {
    /**
     *
     * @return current server time
     */
    LocalDateTime localTime();
}
