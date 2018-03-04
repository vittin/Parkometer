package com.example.touk.toukparkometer.payment;

import com.example.touk.toukparkometer.dao.ParkEventRepository;
import com.example.touk.toukparkometer.dao.model.Customer;
import com.example.touk.toukparkometer.dao.model.ParkEvent;
import com.example.touk.toukparkometer.dao.model.helper.CustomerType;
import com.example.touk.toukparkometer.dao.model.Price;
import com.example.touk.toukparkometer.time.TimeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Currency;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ParkEventRepository parkEventRepository;
    private final TimeProvider timeProvider;

    @Autowired
    public PaymentServiceImpl(ParkEventRepository parkEventRepository, TimeProvider timeProvider) {
        this.parkEventRepository = parkEventRepository;
        this.timeProvider = timeProvider;
    }

    @Override
    public boolean charge(ParkEvent event, Currency currency) {
        double priceValue = calculatePrice(event, currency);
        Price price = new Price(priceValue, currency);
        event.setPrice(price);
        if (chargeClient(event.getCustomer(), price)){
            event.getPrice().markPaid();
            event.setEndDate(timeProvider.localTime());
            parkEventRepository.save(event);
            return true;
        }
        return false;
    }

    @Override
    public double calculatePrice(ParkEvent event, Currency currency) {
        CustomerType customerType = event.getCustomer().getType();
        int parkedTimeInHours = parkedTimeInHours(event.getStartDate());
        double priceValue = calculatePriceValue(customerType, parkedTimeInHours);
        return priceValue * getCurrencyMultiplier(currency);
    }

    private int parkedTimeInHours(LocalDateTime startDate) {
        long parkedTimeInMinutes = ChronoUnit.MINUTES.between(startDate, LocalDateTime.now());
        //assuming that each 60min park start next park hour;
        int fullHours = (int) parkedTimeInMinutes / 60;
        return fullHours + 1;
    }


    private boolean chargeClient(Customer customer, Price price) {
        return externalServicePayment(customer, price);
    }

    /* todo: integrate with api.
    /  todo: @see http://fixer.io/ for more info
    */
    private double getCurrencyMultiplier(Currency currency) {
        double exchangeRate = 1.0;
        switch (currency.getSymbol()) {
            case "USD":
                exchangeRate = (1/3.8);
                logger.debug("Converting to USD using exchange rate 1:{}", exchangeRate);
                break;
        }
        return exchangeRate;
    }

    private double calculatePriceValue(CustomerType customerType, int parkedTimeInHours) {
        double cost = 0.0;
        double lastHourFee = customerType.getPrices().get(customerType.getPrices().size() - 1); //last fixed fee
        for (int i = 0; i < parkedTimeInHours; i++){
            if (i < customerType.getPrices().size()){
                cost += customerType.getPrices().get(i);
            } else {
                lastHourFee *= customerType.getMultiplier();
                cost += lastHourFee;
            }
        }
        return cost;
    }

    //todo: is stub ;)
    private boolean externalServicePayment(Customer customer, Price price) {
        logger.info("Payment successful. {} User {} are charged for: {} {}",
                customer.getType(), customer.getIdentity(), price.getValue(), price.getCurrency());
        return true;
    }
}
