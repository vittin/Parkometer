package com.example.touk.toukparkometer.time;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TimeProviderImpl implements TimeProvider {

    @Value("app.time.zone-id")
    private String zoneId;
    private final ZoneId zone;

    public TimeProviderImpl() {
        this.zone = ZoneId.of(zoneId);
    }

    public TimeProviderImpl(ZoneId zone){
        this.zone = zone;
    }

    @Override
    public LocalDateTime localTime() {
        return LocalDateTime.now(zone);
    }
}
