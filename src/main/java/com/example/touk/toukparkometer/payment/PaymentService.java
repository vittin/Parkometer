package com.example.touk.toukparkometer.payment;

import com.example.touk.toukparkometer.dao.model.ParkEvent;
import org.springframework.transaction.annotation.Transactional;

import java.util.Currency;

public interface PaymentService {
    /**
     *
     * @param parkEvent is park event entity
     * @param currency is one of available payment methods. Actually supported only 'PLN' as a currency
     * @return true if payment are accepted - false otherwise
     */
    @Transactional //should be ;)
    public boolean charge(ParkEvent parkEvent, Currency currency);

    /**
     *
     * @param parkEvent is park event entity
     * @param currency is one of available payment methods. Actually supported only 'PLN' as a currency
     * @return duty for parking time from start to now
     */
    public double calculatePrice(ParkEvent parkEvent, Currency currency);
}

