package com.example.touk.toukparkometer.dao.model;

import com.example.touk.toukparkometer.dao.model.helper.Price;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class ParkEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = Customer.class)
    private Customer customer;
    @NotNull
    private LocalDateTime startDate;
    @OrderColumn
    private LocalDateTime endDate;
    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = Price.class)
    private Price price;

    ParkEvent(){}

    public ParkEvent(Customer customer, LocalDateTime startDate) {
        this.customer = customer;
        this.startDate = startDate;
    }

    public ParkEvent(Customer customer, LocalDateTime startDate, LocalDateTime endDate, Price price) {
        this(customer, startDate);
        this.endDate = endDate;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParkEvent)) return false;
        ParkEvent parkEvent = (ParkEvent) o;
        return Objects.equals(customer, parkEvent.customer) &&
                Objects.equals(startDate, parkEvent.startDate) &&
                Objects.equals(endDate, parkEvent.endDate) &&
                Objects.equals(price, parkEvent.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(customer, startDate, endDate, price);
    }

    @Override
    public String toString() {
        return "ParkEvent{" +
                "id=" + id +
                ", customer=" + customer +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                '}';
    }
}
