package com.example.touk.toukparkometer.operator;

import com.example.touk.toukparkometer.dao.ParkEventRepository;
import com.example.touk.toukparkometer.dao.model.Customer;
import com.example.touk.toukparkometer.dao.model.ParkEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingMeterOperatorServiceImplTest {

    @Autowired
    private ParkingMeterOperatorService parkingMeterOperatorService;
    @MockBean
    ParkEventRepository parkEventRepository;
    Customer exampleCustomer;
    ParkEvent exampleEvent;

    @Before
    public void setUp() throws Exception {
        exampleCustomer = new Customer("test1");
        exampleEvent = new ParkEvent(exampleCustomer, LocalDateTime.of(2018,1,1,22,34));
        given(parkEventRepository.findByCustomerIdentity("test1")).willReturn(exampleEvent);
    }

    @Test
    public void given_customerName_when_getCustomerInfo_then_shouldReturnCustomerPlaces() {
        //given

        //when
        Optional<ParkEvent> parkEvent = parkingMeterOperatorService.parkInfo("test1");
        //then
        assertThat(parkEvent, is(not(nullValue())));
        assertTrue(parkEvent.isPresent());
        assertThat(parkEvent.get(), is(parkEvent));
    }

    @Test
    public void given_wrongCustomerName_when_getCustomerInfo_then_shouldReturnNothing() {
        //given

        //when
        Optional<ParkEvent> parkEvent = parkingMeterOperatorService.parkInfo("test2");
        //then
        assertThat(parkEvent, is(not(nullValue())));
        assertFalse(parkEvent.isPresent());
    }


}