package com.ridango.payment.dao;

import com.ridango.payment.exceptions.AssignmentException;
import com.ridango.payment.model.Payment;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

public interface PaymentDao {
    Payment savePayment(Payment payment) throws AssignmentException;

    Payment getPaymentById(BigInteger paymentId) throws AssignmentException;

    List<Payment> getPaymentBySenderAccountId(BigInteger senderId) throws AssignmentException;

    List<Payment> getPaymentByReceiverAccountId(BigInteger receiverId) throws AssignmentException;

    List<Payment> getPaymentsByTimestamps(Timestamp start, Timestamp end) throws AssignmentException;
}
