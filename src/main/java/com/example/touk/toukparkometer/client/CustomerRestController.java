package com.example.touk.toukparkometer.client;

import com.example.touk.toukparkometer.dao.model.Customer;
import com.example.touk.toukparkometer.dao.model.ParkEvent;
import com.example.touk.toukparkometer.dao.model.Price;
import com.example.touk.toukparkometer.dao.model.helper.CustomerType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.Currency;

@RestController
@RequestMapping("customer/api/v1")
public class CustomerRestController {

    private ParkingMeterCustomerService parkingMeterCustomerService;

    public CustomerRestController(ParkingMeterCustomerService parkingMeterCustomerService) {
        this.parkingMeterCustomerService = parkingMeterCustomerService;
    }

    @GetMapping("/customer/{identity}")
    ResponseEntity<Price> getParkPrice(@PathVariable String customerIdentity,
                                      @RequestParam(name = "type", required = false, defaultValue = "R") String customerTypeShortName){
        CustomerType customerType = CustomerType.fromShortName(customerTypeShortName);
        Customer customer = new Customer(customerIdentity, customerType);
        return ResponseEntity.ok(parkingMeterCustomerService.checkFee(customer));
    }

    @GetMapping("/customer/{identity}")
    ResponseEntity<Price> getParkPriceUsingCurrency(@PathVariable String customerIdentity,
                                    @RequestParam String currencyCode,
                                    @RequestParam(name = "type", required = false, defaultValue = "R") String customerTypeShortName){
        CustomerType customerType = CustomerType.fromShortName(customerTypeShortName);
        Customer customer = new Customer(customerIdentity, customerType);
        Currency currency = Currency.getInstance(currencyCode);
        return ResponseEntity.ok(parkingMeterCustomerService.checkFee(customer, currency));
    }

    @PostMapping("/customer/{identity}")
    ResponseEntity<ParkEvent> startParkEvent(@PathVariable String customerIdentity,
                             @RequestParam(name = "type", required = false, defaultValue = "R") String customerTypeShortName){
        CustomerType customerType = CustomerType.fromShortName(customerTypeShortName);
        Customer customer = new Customer(customerIdentity, customerType);
        UriComponents path = ServletUriComponentsBuilder.fromCurrentRequest().build();
        return ResponseEntity.created(path.toUri()).body(parkingMeterCustomerService.start(customer));
    }

    @PutMapping("/customer/{identity}")
    ResponseEntity<ParkEvent> stopParkEvent(@PathVariable String customerIdentity,
                            @RequestParam(name = "currency", required = false, defaultValue = "PLN") String currencyCode){
        Currency currency = Currency.getInstance(currencyCode);
        Customer customer = new Customer(customerIdentity);
        parkingMeterCustomerService.makePayment(customer, currency);
        UriComponents path = ServletUriComponentsBuilder.fromCurrentRequest().build();
        return ResponseEntity.created(path.toUri()).body(parkingMeterCustomerService.stop(customer));
    }
}
