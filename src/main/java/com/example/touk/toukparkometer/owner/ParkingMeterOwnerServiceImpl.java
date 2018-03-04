package com.example.touk.toukparkometer.owner;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;

@Service
public class ParkingMeterOwnerServiceImpl implements ParkingMeterOwnerService {
    @Override
    public Map<Currency, Double> cashSummary(LocalDate date) {
        return null;
    }

    @Override
    public Double cashSummary(LocalDate date, Currency currency) {
        return null;
    }
}
