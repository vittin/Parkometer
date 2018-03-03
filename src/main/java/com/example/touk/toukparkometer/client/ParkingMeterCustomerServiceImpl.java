package com.example.touk.toukparkometer.client;

import com.example.touk.toukparkometer.dao.model.Customer;
import com.example.touk.toukparkometer.dao.model.ParkEvent;
import com.example.touk.toukparkometer.dao.model.helper.Price;

import java.util.Currency;

public class ParkingMeterCustomerServiceImpl implements ParkingMeterCustomerService {
    @Override
    public ParkEvent start(Customer customer) {
        return null;
    }

    @Override
    public boolean makePayment(Customer customer, Currency currency) {
        return false;
    }

    @Override
    public ParkEvent stop(Customer customer) {
        return null;
    }

    @Override
    public Price checkFee(Customer customer) {
        return null;
    }

    @Override
    public Price checkFee(Customer customer, Currency currency) {
        return null;
    }
}
