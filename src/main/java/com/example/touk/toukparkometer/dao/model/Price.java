package com.example.touk.toukparkometer.dao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Currency;
import java.util.Objects;

@Entity
public class Price {
    @GeneratedValue
    @Id
    private long id;
    //@NotNull //fixme
    private Currency currency;
    //@NotNull
    private Double value;
    //@NotNull
    private boolean paid;

    protected Price(){}

    public Price(@NotNull Double value, @NotNull Currency paymentCurrency) {
        this.currency = paymentCurrency;
        this.value = value;
        this.paid = false;
    }

    public Price(@NotNull Integer value, @NotNull Currency paymentCurrency) {
        this.currency = paymentCurrency;
        this.value = new Double(value);
        this.paid = false;
    }

    public Price markPaid(){
        this.paid = true;
        return this;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;
        Price price = (Price) o;
        return paid == price.paid &&
                Objects.equals(currency, price.currency) &&
                Objects.equals(value, price.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(currency, value, paid);
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", currency=" + currency +
                ", value=" + value +
                ", paid=" + paid +
                '}';
    }
}
