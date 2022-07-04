package com.ridango.payment.model;

import java.math.BigInteger;

public class AccountDto {

    private BigInteger accountId;
    private String name;
    private Double balance;

    public AccountDto(String name, Double balance) {
        this.name = name;
        this.balance = balance;
    }

    public AccountDto() {
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
}
