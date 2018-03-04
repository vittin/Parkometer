package com.example.touk.toukparkometer.operator;

import com.example.touk.toukparkometer.dao.model.Customer;
import com.example.touk.toukparkometer.dao.model.ParkEvent;
import com.example.touk.toukparkometer.dao.model.Price;
import com.example.touk.toukparkometer.dao.model.helper.CustomerType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.Optional;

@RestController
@RequestMapping("/operator/api/v1")
public class OperatorRestController {

    private ParkingMeterOperatorService parkingMeterOperatorService;

    public OperatorRestController(ParkingMeterOperatorService parkingMeterOperatorService) {
        this.parkingMeterOperatorService = parkingMeterOperatorService;
    }

    @GetMapping("/customer/{identity}")
    ResponseEntity<ParkEvent> getParkInfo(@PathVariable String customerIdentity) {
        Optional<ParkEvent> parkEvent = parkingMeterOperatorService.parkInfo(customerIdentity);
        return parkEvent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
