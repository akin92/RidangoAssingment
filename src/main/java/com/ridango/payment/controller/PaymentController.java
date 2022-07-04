package com.ridango.payment.controller;

import com.ridango.payment.dao_impl.AccountService;
import com.ridango.payment.dao_impl.PaymentService;
import com.ridango.payment.enums.ErrorCode;
import com.ridango.payment.exceptions.AssignmentException;
import com.ridango.payment.model.Account;
import com.ridango.payment.model.Payment;
import com.ridango.payment.model.PaymentDto;
import com.ridango.payment.rest_controller.PaymentRestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PaymentController implements PaymentControllerInterface {
    private static final Logger logger = LogManager.getLogger(PaymentController.class);

    @Autowired
    PaymentService paymentService;

    @Autowired
    AccountService accountService;

    @Override
    public Payment validateAndCreatePayment(PaymentDto paymentDto) throws AssignmentException {
        try {
            Double amount = paymentDto.getAmount();
            //validation
            if(amount < 0){
                logger.error("Amount  should not be  negative");
                throw new AssignmentException(ErrorCode.NEGATIVE_AMOUNT);
            }
            Integer successWithdraw = accountService.withdrawBalance(amount, paymentDto.getSenderAccountId());
            Integer successDeposit = accountService.depositBalance(amount, paymentDto.getReceiverAccountId());
            if (successWithdraw == 1 && successDeposit == 1) {
                logger.info("Money Transfer was successfull");
                //sender Account Info
                Account accountSender = accountService.getAccountById(paymentDto.getSenderAccountId());
                //Receive Account Info
                Account  accountReceive= accountService.getAccountById(paymentDto.getReceiverAccountId());
                // getting the system date
                Date date = new Date();

                // getting the object of the Timestamp class
                Timestamp ts = new Timestamp(date.getTime());
                Payment payment = new Payment(accountSender, accountReceive, ts, paymentDto.getAmount());
                return paymentService.savePayment(payment);
            } else {
                throw new AssignmentException(ErrorCode.FAILED_PAYMENT_PROCESS);
            }
        } catch (AssignmentException ae) {
            throw new AssignmentException(ae.getMessage(), ae.getErrorCode());
        }
    }

    @Override
    public Payment getPaymentById(BigInteger paymentId) throws AssignmentException {
        return paymentService.getPaymentById(paymentId);
    }

    @Override
    public List<Payment> getPaymentBySenderAccountId(BigInteger senderId) throws AssignmentException {
        return paymentService.getPaymentBySenderAccountId(senderId);
    }

    @Override
    public List<Payment> getPaymentByReceiverAccountId(BigInteger receiverId) throws AssignmentException {
        return paymentService.getPaymentByReceiverAccountId(receiverId);
    }

    @Override
    public List<Payment> getPaymentsByTimestamps(Date start, Date end) throws AssignmentException {
       Timestamp startTimestamp = new Timestamp(start.getTime());
        Timestamp endTimestamp = new Timestamp(end.getTime());
        return paymentService.getPaymentsByTimestamps(startTimestamp,endTimestamp);
    }
}
