package com.ridango.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import java.math.BigInteger;
import java.sql.Timestamp;

@JsonIgnoreProperties(value={ "timestamp"}, allowGetters= true)
public class PaymentDto {
    private BigInteger senderAccountId;
    private BigInteger receiverAccountId;
    @Min(value = 0, message = "The value must be positive")
    private Double amount;
    @JsonIgnore
    @JsonProperty("timestamp")
    private Timestamp timestamp;

    public PaymentDto(BigInteger senderAccountId, BigInteger receiverAccountId, Double amount, Timestamp timestamp) {
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
        this.timestamp = timestamp;
    }
    public PaymentDto(BigInteger senderAccountId, BigInteger receiverAccountId, Double amount) {
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
    }

    public PaymentDto() {
    }

    public BigInteger getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(BigInteger senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public BigInteger getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(BigInteger receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PaymentDto{" +
                "senderAccountId=" + senderAccountId +
                ", receiverAccountId=" + receiverAccountId +
                ", amount=" + amount +
                '}';
    }
}
