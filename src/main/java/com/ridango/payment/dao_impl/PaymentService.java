package com.ridango.payment.dao_impl;

import com.ridango.payment.dao.PaymentDao;
import com.ridango.payment.enums.ErrorCode;
import com.ridango.payment.exceptions.AssignmentException;
import com.ridango.payment.model.Payment;
import com.ridango.payment.repository.PaymentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentService implements PaymentDao {
    @Autowired
    PaymentRepository paymentRepository;
    private static final Logger logger = LogManager.getLogger(PaymentService.class);

    @Override
    public Payment savePayment(Payment payment) throws AssignmentException {
        try {
            return paymentRepository.save(payment);
        } catch (Exception e) {
            logger.error("Payment Could Add To Db");
            throw new AssignmentException(e.getMessage(), ErrorCode.DATABASE);
        }
    }

    @Override
    public Payment getPaymentById(BigInteger paymentId) throws AssignmentException {
        try {
            Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
            if (paymentOptional.isPresent()) {
                return paymentOptional.get();
            } else {
                throw new AssignmentException(ErrorCode.PAYMENT_DOES_NOT_EXIST);
            }
        } catch (Exception e) {
            logger.error("Payment Could Not Get From Db");
            throw new AssignmentException(e.getMessage(), ErrorCode.DATABASE);
        }
    }

    @Override
    public List<Payment> getPaymentBySenderAccountId(BigInteger senderId) throws AssignmentException {
        try {
            return paymentRepository.listPaymentsSenderAccountId(senderId);
        } catch (Exception e) {
            logger.error("Payment Could Not Get From Db According To SenderAccountId");
            throw new AssignmentException(e.getMessage(), ErrorCode.DATABASE);
        }
    }

    @Override
    public List<Payment> getPaymentByReceiverAccountId(BigInteger receiverId) throws AssignmentException {
        try {
            return paymentRepository.listPaymentsReceiveAccountId(receiverId);
        } catch (Exception e) {
            logger.error("Payment Could Not Get From Db According To ReceiverAccountId");
            throw new AssignmentException(e.getMessage(), ErrorCode.DATABASE);
        }
    }

    @Override
    public List<Payment> getPaymentsByTimestamps(Timestamp start, Timestamp end) throws AssignmentException {
        try {
            return paymentRepository.getPaymentsBetweenTimeStamps(start, end);
        } catch (Exception e) {
            logger.error("Payment Could Not Get From Db According To TimeStamps");
            throw new AssignmentException(e.getMessage(), ErrorCode.DATABASE);
        }
    }
}
