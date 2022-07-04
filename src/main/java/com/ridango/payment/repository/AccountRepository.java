package com.ridango.payment.repository;

import com.ridango.payment.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface AccountRepository extends JpaRepository<Account, BigInteger> {
    @Modifying
    @Query("UPDATE Account SET balance = :balance WHERE id = :accountId")
    Integer updateBalance(Double balance, BigInteger accountId);
}
