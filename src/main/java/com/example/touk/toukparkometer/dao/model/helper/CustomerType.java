package com.example.touk.toukparkometer.dao.model.helper;

import java.util.Arrays;
import java.util.List;

public enum CustomerType {
    REGULAR(Arrays.asList(1.0,2.0), 2), VIP(Arrays.asList(0.0, 2.0), 1.5);

    private final List<Double> prices;
    private final double multiplier;

    CustomerType(List<Double> prices, double multiplier){
        this.prices = prices;
        this.multiplier = multiplier;
    }

    public List<Double> getPrices() {
        return prices;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public static String toShortName(CustomerType customerType) {
        switch (customerType) {
            case REGULAR: return "R";
            case VIP: return "V";
            default: return "R";
        }
    }

    public static CustomerType fromShortName(String customerType){
        switch (customerType.toUpperCase()) {
            case "R": case "REGULAR":
                return REGULAR;
            case "V": case "VIP":
                return VIP;
            default: return REGULAR;
        }
    }
}
