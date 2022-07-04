package com.ridango.payment.repository;

import com.ridango.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, BigInteger> {
    @Query(value = "select * from Payment   where sender_account_id = :senderAccountId",nativeQuery = true)
    List<Payment>listPaymentsSenderAccountId(@Param("senderAccountId") BigInteger senderAccountId);
    @Query(value = "select * from Payment   where receiver_account_id = :receiverAccountId",nativeQuery = true)
    List<Payment>listPaymentsReceiveAccountId(@Param("receiverAccountId") BigInteger receiverAccountId);

    @Query("select p from Payment p  where p.timestamp BETWEEN :startTimestamp AND :endTimestamp")
    List<Payment> getPaymentsBetweenTimeStamps(Timestamp startTimestamp , Timestamp endTimestamp);
}
