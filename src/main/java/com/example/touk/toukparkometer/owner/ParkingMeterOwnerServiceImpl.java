package com.example.touk.toukparkometer.owner;

import com.example.touk.toukparkometer.dao.ParkEventRepository;
import com.example.touk.toukparkometer.dao.model.ParkEvent;
import com.example.touk.toukparkometer.dao.model.Price;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ParkingMeterOwnerServiceImpl implements ParkingMeterOwnerService {

    private ParkEventRepository parkEventRepository;

    public ParkingMeterOwnerServiceImpl(ParkEventRepository parkEventRepository) {
        this.parkEventRepository = parkEventRepository;
    }

    @Override
    public Map<Currency, Double> cashSummary(LocalDate date) {
        return parkEventRepository.findParkEventsFinishedAt(date).stream()
                .map(ParkEvent::getPrice)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Price::getCurrency, Price::getValue, (v1, v2) -> v1 + v2));
    }

    @Override
    public Double cashSummary(LocalDate date, Currency currency) {
        return parkEventRepository.findParkEventsFinishedAt(date).stream()
                .map(ParkEvent::getPrice)
                .filter(Objects::nonNull)
                .filter(p -> p.getCurrency().equals(currency))
                .mapToDouble(Price::getValue)
                .sum();

    }
}
