package com.ridango.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "payment")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;


    @JoinColumn(name = "sender_account_id")
    @ManyToOne(cascade=CascadeType.ALL,fetch= FetchType.LAZY)
    private Account senderAccountId;
    @JoinColumn(name = "receiver_account_id")
    @ManyToOne(cascade=CascadeType.ALL,fetch= FetchType.LAZY)
    private Account receiverAccountId;

    @Column(nullable = false)
    private Timestamp timestamp;

    @Column(nullable = false)
    @Min(value = 0, message = "The value must be positive")
    private Double amount;

    public Payment() {
    }

    public Payment(Account senderAccountId, Account receiverAccountId, Timestamp timestamp, Double amount) {
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Account getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(Account senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public Account getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(Account receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", senderAccountId=" + senderAccountId +
                ", receiverAccountId=" + receiverAccountId +
                ", timestamp=" + timestamp +
                ", amount=" + amount +
                '}';
    }
}
