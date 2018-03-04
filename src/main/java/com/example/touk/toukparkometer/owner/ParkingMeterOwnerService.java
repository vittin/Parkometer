package com.example.touk.toukparkometer.owner;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;

public interface ParkingMeterOwnerService {

    /**
     *
     * @param date is the selected day from past.
     * @return all paid events between 00:00 and 23:59 of date, grouped by payment currency
     */
    Map<Currency, Double> cashSummary(LocalDate date);

    /**
     *
     * @param date date is the selected day from past.
     * @param currency is one on available payment currency
     * @return all paid events between 00:00 and 23:59 of date, payed by selected currency
     */
    Double cashSummary(LocalDate date, Currency currency);
}
