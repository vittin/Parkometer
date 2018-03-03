package com.example.touk.toukparkometer.payment;

import com.example.touk.toukparkometer.dao.model.ParkEvent;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public boolean charge(ParkEvent parkEvent, Currency currency) {
        return false;
    }

    @Override
    public double calculatePrice(ParkEvent parkEvent, Currency currency) {
        return 0;
    }
}
