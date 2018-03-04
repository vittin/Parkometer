package com.example.touk.toukparkometer.client;

import com.example.touk.toukparkometer.dao.model.Customer;
import com.example.touk.toukparkometer.dao.model.ParkEvent;
import com.example.touk.toukparkometer.dao.model.Price;

import java.util.Currency;
import java.util.Optional;

public interface ParkingMeterCustomerService {
    /**
     * @param customer is unique user which want to perform operation
     * @return parkEvent model
     */
    ParkEvent start(Customer customer);

    /**
     *
     * @param customer is unique user which want to perform operation
     * @param currency is one of available payment methods. Actually supported only 'PLN' as a currency
     * @return true if payment is successfull, otherwise false
     */
    boolean makePayment(Customer customer, Currency currency);

    /**
     *
     * @param customer is unique user which want to perform operation
     * @return parkEvent model
     */
    Optional<ParkEvent> stop(Customer customer);

    /**
     * @param customer is unique user which want to perform operation
     * @return duty for parked place. Using default 'PLN' currency.
     */
    Optional<Price> checkFee(Customer customer);

    /**
     *
     * @param customer is unique user which want to perform operation
     * @param currency is one of available payment methods. Actually supported only 'PLN' as a currency
     * @return duty for all parked places, grouped by given currency
     */
    Optional<Price> checkFee(Customer customer, Currency currency);
}
