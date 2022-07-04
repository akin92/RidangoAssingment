package com.ridango.payment.controller;

import com.ridango.payment.exceptions.AssignmentException;
import com.ridango.payment.model.Payment;
import com.ridango.payment.model.PaymentDto;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface PaymentControllerInterface {
    Payment validateAndCreatePayment(PaymentDto paymentDto) throws AssignmentException;

    Payment getPaymentById(BigInteger paymentId) throws AssignmentException;

    List<Payment> getPaymentBySenderAccountId(BigInteger senderId) throws AssignmentException;

    List<Payment> getPaymentByReceiverAccountId(BigInteger receiverId) throws AssignmentException;

    List<Payment> getPaymentsByTimestamps(Date start, Date end) throws AssignmentException;
}
