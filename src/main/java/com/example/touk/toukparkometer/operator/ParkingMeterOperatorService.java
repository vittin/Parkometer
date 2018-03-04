package com.example.touk.toukparkometer.operator;

import com.example.touk.toukparkometer.dao.model.ParkEvent;

import java.util.Optional;

public interface ParkingMeterOperatorService {
    Optional<ParkEvent> parkInfo(String customerIdentity);
}
