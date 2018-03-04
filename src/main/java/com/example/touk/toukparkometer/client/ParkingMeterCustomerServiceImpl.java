package com.example.touk.toukparkometer.client;

import com.example.touk.toukparkometer.dao.ParkEventRepository;
import com.example.touk.toukparkometer.dao.model.Customer;
import com.example.touk.toukparkometer.dao.model.ParkEvent;
import com.example.touk.toukparkometer.dao.model.Price;
import com.example.touk.toukparkometer.payment.PaymentService;
import com.example.touk.toukparkometer.payment.PaymentServiceImpl;
import com.example.touk.toukparkometer.time.TimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Currency;
import java.util.Optional;

@Service
public class ParkingMeterCustomerServiceImpl implements ParkingMeterCustomerService {

    @Value("${app.currency.default}")
    private String defaultCurrencyCode;
    private ParkEventRepository parkEventRepository;
    private TimeProvider timeProvider;
    private PaymentService paymentService;

    @Autowired
    public ParkingMeterCustomerServiceImpl(ParkEventRepository parkEventRepository,
                                           TimeProvider timeProvider,
                                           PaymentService paymentService) {
        this.parkEventRepository = parkEventRepository;
        this.timeProvider = timeProvider;
        this.paymentService = paymentService;
    }

    @Override
    public ParkEvent start(Customer customer) {
        ParkEvent parkEvent = new ParkEvent(customer, timeProvider.localTime());
        return parkEventRepository.save(parkEvent);
    }

    @Override
    public boolean makePayment(Customer customer, Currency currency) {
        Optional<ParkEvent> parkEvent = parkEventRepository.findByCustomerIdentity(customer.getIdentity());
        return !parkEvent.isPresent() || paymentService.charge(parkEvent.get(), currency);
    }

    @Override
    public Optional<ParkEvent> stop(Customer customer) {
        Optional<ParkEvent> parkEvent = parkEventRepository.findByCustomerIdentity(customer.getIdentity());
        if (parkEvent.isPresent() && !isPricePaid(parkEvent.get())) {
            //assuming that its always true and never throw any exception
            paymentService.charge(parkEvent.get(), Currency.getInstance(defaultCurrencyCode));
        }
        return parkEvent;
    }

    @Override
    public Optional<Price> checkFee(Customer customer) {
        Currency currency = Currency.getInstance(defaultCurrencyCode);
        return checkFee(customer, currency);
    }

    @Override
    public Optional<Price> checkFee(Customer customer, Currency currency) {
        Optional<ParkEvent> parkEvent = parkEventRepository.findByCustomerIdentity(customer.getIdentity());
        return parkEvent.map(e -> new Price(paymentService.calculatePrice(e, currency), currency));
    }

    private boolean isPricePaid(ParkEvent parkEvent) {
        return parkEvent.getPrice() != null &&  parkEvent.getPrice().isPaid();
    }
}
