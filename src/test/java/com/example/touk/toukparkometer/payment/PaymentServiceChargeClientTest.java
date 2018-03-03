package com.example.touk.toukparkometer.payment;

import com.example.touk.toukparkometer.dao.ParkEventRepository;
import com.example.touk.toukparkometer.dao.model.Customer;
import com.example.touk.toukparkometer.dao.model.ParkEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource("/application.properties")
public class PaymentServiceChargeClientTest {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ParkEventRepository parkEventRepository;
    private Customer regularCustomer;
    private Customer vipCustomer;

    @Before
    public void setUp() {
        regularCustomer = new Customer("KRK 7877");
    }

    @Test
    public void given_userWithParkTime_when_charge_shouldBeSuccessful() {
        ParkEvent parkEvent = new ParkEvent(regularCustomer, LocalDateTime.now());
        parkEvent = parkEventRepository.save(parkEvent);

        boolean chargeSuccess = paymentService.charge(parkEvent, Currency.getInstance("PLN"));

        assertTrue(chargeSuccess);
    }

    @Test
    public void given_userWithParkTime_when_charge_shouldModifyEntity() {
        ParkEvent parkEvent = new ParkEvent(regularCustomer, LocalDateTime.now());
        parkEvent = parkEventRepository.save(parkEvent);

        paymentService.charge(parkEvent, Currency.getInstance("PLN"));

        Optional<ParkEvent> afterCharge = parkEventRepository.findById(parkEvent.getId());
        assertTrue(afterCharge.isPresent());
        assertThat(afterCharge.get(), is(not(equalTo(parkEvent))));
    }

    @Test
    public void given_userWithParkTime_when_charge_shouldSetEndDate() {
        ParkEvent parkEvent = new ParkEvent(regularCustomer, LocalDateTime.now());
        parkEvent = parkEventRepository.save(parkEvent);

        paymentService.charge(parkEvent, Currency.getInstance("PLN"));

        Optional<ParkEvent> afterCharge = parkEventRepository.findById(parkEvent.getId());
        assertTrue(afterCharge.isPresent());
        assertThat(afterCharge.get().getEndDate(), is(not(nullValue())));
    }

    @Test
    public void given_userWithParkTime_when_charge_shouldSetPrice() {
        ParkEvent parkEvent = new ParkEvent(regularCustomer, LocalDateTime.now());
        parkEvent = parkEventRepository.save(parkEvent);

        paymentService.charge(parkEvent, Currency.getInstance("PLN"));

        Optional<ParkEvent> afterCharge = parkEventRepository.findById(parkEvent.getId());
        assertTrue(afterCharge.isPresent());
        assertThat(afterCharge.get().getPrice(), is(not(nullValue())));
    }


}
