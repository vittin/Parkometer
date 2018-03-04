package com.example.touk.toukparkometer.operator;

import com.example.touk.toukparkometer.dao.model.ParkEvent;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingMeterOperatorServiceImpl implements ParkingMeterOperatorService {
    @Override
    public Optional<ParkEvent> parkInfo(String customerIdentity) {
        return Optional.empty();
    }
}
