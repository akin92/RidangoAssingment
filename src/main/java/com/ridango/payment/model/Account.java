package com.ridango.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Table(name = "account")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Column(nullable = false,length = 50)
    private String name;

    @Column(nullable = false)
    @Min(value = 0, message = "The value must be positive")
    private Double balance;

    public Account() {
    }

    public Account(String name, Double balance) {
        this.name = name;
        this.balance = balance;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
