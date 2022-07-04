package com.ridango.payment.rest_controller;

import com.ridango.payment.controller.PaymentController;
import com.ridango.payment.dao_impl.AccountService;
import com.ridango.payment.dao_impl.PaymentService;
import com.ridango.payment.enums.ErrorCode;
import com.ridango.payment.exceptions.AssignmentException;
import com.ridango.payment.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PaymentRestController {
    private static final Logger logger = LogManager.getLogger(PaymentRestController.class);
    @Autowired
    PaymentController paymentController;

    @GetMapping("/payment/{paymentId}")
    @ResponseBody
    public ResponseEntity<PaymentDto> getPayment(@PathVariable BigInteger paymentId) {
        try {
            Payment payment = paymentController.getPaymentById(paymentId);
            PaymentDto paymentDto = new PaymentDto(payment.getSenderAccountId().getId(), payment.getReceiverAccountId().getId(), payment.getAmount(), payment.getTimestamp());
            return new ResponseEntity<>(paymentDto, HttpStatus.OK);
        } catch (AssignmentException cce) {
            return new ResponseEntity(new ResponseError(cce.getErrorCode().getCode(), cce.getErrorCode().getDescription()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseError(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/payment/sender/{senderAccountId}")
    @ResponseBody
    public ResponseEntity<List<PaymentDto>> getPaymentsBySenderId(@PathVariable BigInteger senderAccountId) {
        try {
            List<Payment> payments = paymentController.getPaymentBySenderAccountId(senderAccountId);
            List<PaymentDto> paymentDtos = new ArrayList<>();
            payments.forEach(e -> paymentDtos.add(new PaymentDto(e.getSenderAccountId().getId(), e.getReceiverAccountId().getId(), e.getAmount(), e.getTimestamp())));
            return new ResponseEntity<>(paymentDtos, HttpStatus.OK);
        } catch (AssignmentException cce) {
            return new ResponseEntity(new ResponseError(cce.getErrorCode().getCode(), cce.getErrorCode().getDescription()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseError(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/payment/receiver/{receiveAccountId}")
    @ResponseBody
    public ResponseEntity<List<PaymentDto>> getPaymentsByReceiveId(@PathVariable BigInteger receiveAccountId) {
        try {
            List<Payment> payments = paymentController.getPaymentByReceiverAccountId(receiveAccountId);
            List<PaymentDto> paymentDtos = new ArrayList<>();
            payments.forEach(e -> paymentDtos.add(new PaymentDto(e.getSenderAccountId().getId(), e.getReceiverAccountId().getId(), e.getAmount(), e.getTimestamp())));
            return new ResponseEntity<>(paymentDtos, HttpStatus.OK);
        } catch (AssignmentException cce) {
            return new ResponseEntity(new ResponseError(cce.getErrorCode().getCode(), cce.getErrorCode().getDescription()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseError(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/payment/{startDate}/{endDate}")
    @ResponseBody
    public ResponseEntity<List<PaymentDto>> getPaymentsBetweenStartAndEndDate(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @PathVariable Date startDate, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  @PathVariable Date endDate) {
        try {
            List<Payment> payments = paymentController.getPaymentsByTimestamps(startDate,endDate);
            List<PaymentDto> paymentDtos = new ArrayList<>();
            payments.forEach(e -> paymentDtos.add(new PaymentDto(e.getSenderAccountId().getId(), e.getReceiverAccountId().getId(), e.getAmount(), e.getTimestamp())));
            return new ResponseEntity<>(paymentDtos, HttpStatus.OK);
        } catch (AssignmentException cce) {
            return new ResponseEntity(new ResponseError(cce.getErrorCode().getCode(), cce.getErrorCode().getDescription()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseError(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/payment")
    @ResponseBody
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentDto paymentDto) {
        try {
            Payment createdPayment = paymentController.validateAndCreatePayment(paymentDto);
            PaymentDto responsePaymentDto = new PaymentDto(paymentDto.getSenderAccountId(), paymentDto.getReceiverAccountId(), paymentDto.getAmount(), createdPayment.getTimestamp());
            return new ResponseEntity<>(responsePaymentDto, HttpStatus.CREATED);
        } catch (AssignmentException cce) {
            return new ResponseEntity(new ResponseError(cce.getErrorCode().getCode(), cce.getErrorCode().getDescription()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseError(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
