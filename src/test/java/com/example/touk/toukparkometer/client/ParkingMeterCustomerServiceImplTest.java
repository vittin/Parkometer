package com.example.touk.toukparkometer.client;

import com.example.touk.toukparkometer.dao.ParkEventRepository;
import com.example.touk.toukparkometer.dao.model.Customer;
import com.example.touk.toukparkometer.dao.model.ParkEvent;
import com.example.touk.toukparkometer.dao.model.Price;
import com.example.touk.toukparkometer.payment.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Currency;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingMeterCustomerServiceImplTest {

    @Autowired
    private ParkingMeterCustomerServiceImpl parkingMeterCustomerService;
    @MockBean
    private ParkEventRepository parkEventRepository;
    @SpyBean
    private PaymentService paymentService;
    private Customer customer;

    @Before
    public void setUp() throws Exception {
        customer = new Customer("TSK 27010");
        //always save
        given(parkEventRepository.save(any(ParkEvent.class))).willAnswer(a -> a.getArgument(0));
        //always find someone
        given(parkEventRepository.findByCustomerIdentity(anyString())).willAnswer(a ->
                new ParkEvent(
                        new Customer(a.getArgument(0)),
                        LocalDateTime.now().minus(2, ChronoUnit.HOURS)
                )
        );
        //always find someone
        given(parkEventRepository.getOne(anyLong())).willAnswer(a ->
        {
            ParkEvent parkEvent = new ParkEvent(customer, LocalDateTime.now().minus(2, ChronoUnit.HOURS));
            parkEvent.setId(a.getArgument(0));
            return parkEvent;
        });
    }

    @Test
    public void when_startParking_then_createParkEvent() {

        ParkEvent parkEvent = parkingMeterCustomerService.start(customer);

        verify(parkEventRepository).save(any(ParkEvent.class));
        assertThat(parkEvent, is(not(nullValue())));
        assertThat(parkEvent.getCustomer(), is(customer));
    }

    @Test
    public void when_stopParking_then_updateParkEvent() {

        ParkEvent parkEvent = parkingMeterCustomerService.stop(customer);

        verify(parkEventRepository).save(any(ParkEvent.class));
        assertThat(parkEvent, is(not(nullValue())));
        assertThat(parkEvent.getCustomer(), is(customer));
    }

    @Test
    public void when_checkFee_then_returnPrice() {
        Price price = parkingMeterCustomerService.checkFee(customer);

        verify(parkEventRepository).findByCustomerIdentity(anyString());
        assertThat(price, is(not(nullValue())));
        assertThat(price.getValue(), is(greaterThan(0.0)));
    }

    @Test
    public void when_checkFeeWithSpecifiedCurrency_then_returnPriceInThisCurrency() {
        Currency currency = Currency.getInstance("USD");
        Price price = parkingMeterCustomerService.checkFee(customer, currency);

        verify(parkEventRepository).findByCustomerIdentity(anyString());
        assertThat(price, is(not(nullValue())));
        assertThat(price.getValue(), is(greaterThan(0.0)));
        assertThat(price.getCurrency(), is(currency));
    }

    @Test
    public void when_makePayment_then_paymentPass() {
        Currency currency = Currency.getInstance("PLN");
        boolean paymentSuccess = parkingMeterCustomerService.makePayment(customer, currency);

        verify(parkEventRepository).findByCustomerIdentity(anyString());
        verify(parkEventRepository).save(any(ParkEvent.class));
        assertTrue(paymentSuccess);
    }
}