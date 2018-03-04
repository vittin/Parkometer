package com.example.touk.toukparkometer.owner;

import com.example.touk.toukparkometer.dao.model.Price;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;

@RestController
@RequestMapping("/owner/api/v1")
public class OwnerRestController {

    private ParkingMeterOwnerService parkingMeterOwnerService;

    public OwnerRestController(ParkingMeterOwnerService parkingMeterOwnerService) {
        this.parkingMeterOwnerService = parkingMeterOwnerService;
    }

    @GetMapping("/cash/day/{date}")
    ResponseEntity<Map<Currency, Double>> getParkPrice(@PathVariable @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) String date){
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(parkingMeterOwnerService.cashSummary(localDate));
    }

    @GetMapping("/cash/day/{date}/currency/{currency}")
    ResponseEntity<Price> getParkPriceUsingSpecifiedCurrency(@PathVariable @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) String date,
                                                    @PathVariable String currencyCode){
        LocalDate localDate = LocalDate.parse(date);
        Currency currency = Currency.getInstance(currencyCode);
        return ResponseEntity.ok(new Price(
                parkingMeterOwnerService.cashSummary(localDate, currency),
                currency));
    }
}
