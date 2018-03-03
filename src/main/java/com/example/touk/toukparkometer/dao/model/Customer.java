package com.example.touk.toukparkometer.dao.model;

import com.example.touk.toukparkometer.dao.model.helper.CustomerType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Customer {
    @Column(unique = true, nullable = false)
    @Id
    private String identity;

    private CustomerType type;

    protected Customer() {
    }

    public Customer(String identity) {
        this.identity = identity;
        this.type = CustomerType.REGULAR;
    }

    public Customer(String identity, CustomerType type) {
        this.identity = identity;
        this.type = type;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(identity, customer.identity) &&
                type == customer.type;
    }

    @Override
    public int hashCode() {

        return Objects.hash(identity, type);
    }

    @Override
    public String toString() {
        return "Customer{" +
                ", identity='" + identity + '\'' +
                ", type=" + type +
                '}';
    }
}
