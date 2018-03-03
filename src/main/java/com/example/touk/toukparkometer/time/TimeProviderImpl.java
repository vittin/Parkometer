package com.example.touk.toukparkometer.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TimeProviderImpl implements TimeProvider {

    private final ZoneOffset zone;

    @Autowired
    public TimeProviderImpl(@Value("${app.time.zone-offset}") String zone){
        this.zone = ZoneOffset.of(zone);
    }

    public TimeProviderImpl(ZoneOffset zone){
        this.zone = zone;
    }

    @Override
    public LocalDateTime localTime() {
        return LocalDateTime.now(zone);
    }
}
