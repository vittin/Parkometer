package com.example.touk.toukparkometer.owner;

import com.example.touk.toukparkometer.dao.ParkEventRepository;
import com.example.touk.toukparkometer.dao.model.Customer;
import com.example.touk.toukparkometer.dao.model.ParkEvent;
import com.example.touk.toukparkometer.dao.model.Price;
import com.example.touk.toukparkometer.dao.model.helper.CustomerType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingMeterOwnerServiceImplTest {

    @Autowired
    private ParkingMeterOwnerService parkingMeterOwnerService;
    @MockBean
    private ParkEventRepository parkRepository;

    private List<ParkEvent> exampleDay;
    private LocalDateTime timeNow;
    private Price c1PLNPrice;
    private Price c2USDPrice;

    @Before
    public void setUp() throws Exception {
        timeNow = LocalDateTime.of(2018, 1, 1, 23, 59);
        c1PLNPrice = new Price(20, Currency.getInstance("PLN"));
        c2USDPrice = new Price(7, Currency.getInstance("USD"));

        ParkEvent event1 = new ParkEvent(new Customer("c1"), timeNow);
        ParkEvent event2 = new ParkEvent(new Customer("c2"), timeNow);
        ParkEvent event3 = new ParkEvent(new Customer("c3", CustomerType.VIP), timeNow);
        event1.setPrice(c1PLNPrice);
        event1.setEndDate(timeNow.plus(30, ChronoUnit.MINUTES));
        event2.setPrice(c2USDPrice);
        event2.setEndDate(timeNow.plus(5, ChronoUnit.HOURS));
        event3.setPrice(c1PLNPrice);
        event3.setEndDate(timeNow.plus(5, ChronoUnit.HOURS));
        exampleDay = Arrays.asList(event1, event2, event3);
    }

    @Test
    public void given_emptyDB_when_getCashSummary_then_shouldReturnEmptyMap() {
        //given
        //when
        Map<Currency, Double> cashSummary = parkingMeterOwnerService.cashSummary(timeNow.toLocalDate());

        //then
        assertThat(cashSummary, is(not(nullValue())));
        assertThat(cashSummary.keySet(), is(empty()));
    }

    @Test
    public void given_event_when_getCashSummary_shouldReturnCash() {
        //given
        given(parkRepository.findParkEventsFinishedAt(timeNow.toLocalDate())) //simulate full day
                .willReturn(exampleDay.subList(0,1));
        //when
        Map<Currency, Double> cashSummary = parkingMeterOwnerService.cashSummary(timeNow.toLocalDate());
        //then
        assertThat(cashSummary, is(not(nullValue())));
        assertThat(cashSummary, hasEntry(is(Currency.getInstance("PLN")), is(closeTo(20.0, 0.0001))));
    }

    @Test
    public void given_multipleEvents_when_getCashSummaryWithCurrency_shouldReturnCash() {
        //given
        given(parkRepository.findParkEventsFinishedAt(timeNow.toLocalDate())) //simulate full day/simulate full day
                .willReturn(exampleDay);
        //when
        Double cashSummary = parkingMeterOwnerService.cashSummary(timeNow.toLocalDate(), c1PLNPrice.getCurrency());
        //then
        assertThat(cashSummary, is(not(nullValue())));
        //2 events finished using PLN currency
        assertThat(cashSummary, closeTo(2*20.0, 0.0001));
    }

    @Test
    public void given_multipleEvents_when_getCashSummary_shouldReturnSummaryCash() {
        //given
        given(parkRepository.findParkEventsFinishedAt(timeNow.toLocalDate())) //simulate full day //simulate full day
                .willReturn(exampleDay);
        //when
        Map<Currency, Double> cashSummary = parkingMeterOwnerService.cashSummary(timeNow.toLocalDate());
        //then
        assertThat(cashSummary, is(not(nullValue())));
        assertThat(cashSummary, hasEntry(is(Currency.getInstance("PLN")), is(closeTo(40.0, 0.0001))));
        assertThat(cashSummary, hasEntry(is(Currency.getInstance("USD")), is(closeTo(7.0, 0.0001))));
    }
}