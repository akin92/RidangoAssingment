package com.ridango.payment.dao;

import com.ridango.payment.exceptions.AssignmentException;
import com.ridango.payment.model.Account;

import java.math.BigInteger;

public interface AccountDao {
    Account getAccountById(BigInteger accountId)throws AssignmentException;
    Account saveAccount(Account account)throws AssignmentException;
    Integer depositBalance(Double balance, BigInteger accountId)throws AssignmentException;

    Integer withdrawBalance(Double balance, BigInteger accountId)throws AssignmentException;
}
