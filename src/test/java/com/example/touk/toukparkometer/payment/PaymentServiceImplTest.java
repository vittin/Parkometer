package com.example.touk.toukparkometer.payment;

import com.example.touk.toukparkometer.dao.model.Customer;
import com.example.touk.toukparkometer.dao.model.ParkEvent;
import com.example.touk.toukparkometer.dao.model.helper.CustomerType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Currency;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.IsCloseTo.closeTo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentServiceImplTest {

    @Autowired
    private PaymentService paymentService;

    private Customer regularCustomer;
    private Customer vipCustomer;
    
    @Before
    public void setUp() {
        regularCustomer = new Customer("KRK 7877");
        vipCustomer = new Customer("WAW 1337", CustomerType.VIP);
    }

    @Test
    public void given_regularUserWithParkTimeZero_when_checkFee_shouldReturnFeeForOneHour() {
        ParkEvent parkEvent = new ParkEvent(regularCustomer, LocalDateTime.now());

        double price = paymentService.calculatePrice(parkEvent, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo(1.0, 0.0001)));
    }

    @Test
    public void given_vipUserWithParkTimeZero_when_checkFee_shouldReturnFeeForOneHour() {
        ParkEvent parkEvent = new ParkEvent(vipCustomer, LocalDateTime.now());

        double price = paymentService.calculatePrice(parkEvent, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo(0.0, 0.0001)));
    }

    @Test
    public void given_regularUserWithParkTime30min_when_checkFee_shouldReturnFeeForOneHour() {
        ParkEvent parkEvent = new ParkEvent(regularCustomer, LocalDateTime.now().minus(30, ChronoUnit.MINUTES));

        double price = paymentService.calculatePrice(parkEvent, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo(1.0, 0.0001)));
    }

    @Test
    public void given_vipUserWithParkTime30min_when_checkFee_shouldReturnFeeForOneHour() {
        ParkEvent parkEvent = new ParkEvent(vipCustomer, LocalDateTime.now().minus(30, ChronoUnit.MINUTES));

        double price = paymentService.calculatePrice(parkEvent, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo(0.0, 0.0001)));
    }

    @Test
    public void given_regularUserWithParkTime60min_when_checkFee_shouldReturnFeeForTwoHours() {
        ParkEvent parkEvent = new ParkEvent(regularCustomer, LocalDateTime.now().minus(60, ChronoUnit.MINUTES));

        double price = paymentService.calculatePrice(parkEvent, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo(3.0, 0.0001)));
    }

    @Test
    public void given_vipUserWithParkTime60min_when_checkFee_shouldReturnFeeForTwoHours() {
        ParkEvent parkEvent = new ParkEvent(vipCustomer, LocalDateTime.now().minus(60, ChronoUnit.MINUTES));

        double price = paymentService.calculatePrice(parkEvent, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo(2.0, 0.0001)));
    }

    @Test
    public void given_regularUserWithParkTime100min_when_checkFee_shouldReturnFeeForTwoHours() {
        ParkEvent parkEvent = new ParkEvent(regularCustomer, LocalDateTime.now().minus(100, ChronoUnit.MINUTES));

        double price = paymentService.calculatePrice(parkEvent, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo(3.0, 0.0001)));
    }

    @Test
    public void given_vipUserWithParkTime100min_when_checkFee_shouldReturnFeeForTwoHours() {
        ParkEvent parkEvent = new ParkEvent(vipCustomer, LocalDateTime.now().minus(100, ChronoUnit.MINUTES));

        double price = paymentService.calculatePrice(parkEvent, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo(2.0, 0.0001)));
    }

    @Test
    public void given_regularUserWithParkTime120min_when_checkFee_shouldReturnFeeForThreeHours() {
        ParkEvent parkEvent = new ParkEvent(regularCustomer, LocalDateTime.now().minus(120, ChronoUnit.MINUTES));

        double price = paymentService.calculatePrice(parkEvent, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo((1.0) + (2.0) + (2.0*2.0), 0.0001)));
    }

    @Test
    public void given_vipUserWithParkTime120min_when_checkFee_shouldReturnFeeForThreeHours() {
        ParkEvent parkEvent = new ParkEvent(vipCustomer, LocalDateTime.now().minus(120, ChronoUnit.MINUTES));

        double price = paymentService.calculatePrice(parkEvent, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo((0.0) + (2.0) + (2.0*1.5), 0.0001)));
    }

    @Test
    public void given_regularUserWithParkTime320min_when_checkFee_shouldReturnFeeForSixHours() {
        ParkEvent parkEvent = new ParkEvent(regularCustomer, LocalDateTime.now().minus(320, ChronoUnit.MINUTES));

        double price = paymentService.calculatePrice(parkEvent, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo((1.0) + (2.0) + (2.0 * 2) + (2.0 * 2*2) + + (2.0 * 2*2*2) + (2.0 * 2*2*2*2),  0.0001)));
    }

    @Test
    public void given_vipUserWithParkTime320min_when_checkFee_shouldReturnFeeForSixHours() {
        ParkEvent parkEvent = new ParkEvent(vipCustomer, LocalDateTime.now().minus(320, ChronoUnit.MINUTES));

        double price = paymentService.calculatePrice(parkEvent, Currency.getInstance("PLN"));

        assertThat(price, is(closeTo(
                (0.0) + (2.0) + (2.0 * 1.5) + (2.0 * 1.5*1.5) + (2.0 * 1.5*1.5*1.5) + (2.0 * 1.5*1.5*1.5*1.5),
                0.0001)));
    }
}