package com.example.touk.toukparkometer.operator;

import com.example.touk.toukparkometer.dao.ParkEventRepository;
import com.example.touk.toukparkometer.dao.model.ParkEvent;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingMeterOperatorServiceImpl implements ParkingMeterOperatorService {

    private ParkEventRepository parkEventRepository;

    public ParkingMeterOperatorServiceImpl(ParkEventRepository parkEventRepository) {
        this.parkEventRepository = parkEventRepository;
    }

    @Override
    public Optional<ParkEvent> parkInfo(String customerIdentity) {
        return parkEventRepository.findByCustomerIdentity(customerIdentity);
    }
}
