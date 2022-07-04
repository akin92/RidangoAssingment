package com.ridango.payment.dao_impl;

import com.ridango.payment.dao.AccountDao;
import com.ridango.payment.enums.ErrorCode;
import com.ridango.payment.exceptions.AssignmentException;
import com.ridango.payment.model.Account;
import com.ridango.payment.repository.AccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Optional;

@Transactional
@Service
public class AccountService implements AccountDao {
    private static final Logger logger = LogManager.getLogger(AccountDao.class);

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account getAccountById(BigInteger id) throws AssignmentException {
        try {
            Optional<Account> accountOptional = accountRepository.findById(id);
            if (accountOptional.isPresent()) {
                return accountOptional.get();
            } else {
                logger.error("Account Does Not Exist On Db");
                throw new AssignmentException(ErrorCode.ACCOUNT_DOES_NOT_EXIST);
            }
        } catch (AssignmentException e) {
            throw new AssignmentException(e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("Account Could Not Get From Db");
            throw new AssignmentException(e.getMessage(), ErrorCode.DATABASE);
        }
    }

    @Override
    public Account saveAccount(Account account) throws AssignmentException {
        try {
            return accountRepository.save(account);
        } catch (Exception e) {
            logger.error("Account Could Add To Db");
            throw new AssignmentException(e.getMessage(), ErrorCode.DATABASE);
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Integer depositBalance(Double balance, BigInteger accountId) throws AssignmentException {
        try {
            Account account = getAccountById(accountId);
            Double newBalance = account.getBalance() + balance;
            return accountRepository.updateBalance(newBalance, accountId);
        } catch (Exception e) {
            throw new AssignmentException(e.getMessage(), ErrorCode.DATABASE);
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Integer withdrawBalance(Double balance, BigInteger accountId) throws AssignmentException {
        try {
            Account account = getAccountById(accountId);
            if (account.getBalance() - balance < 0) {
                logger.warn("Account does not have enough balance");
                throw new AssignmentException(ErrorCode.INSUFFICENT_BALANCE);
            }
            Double newBalance = account.getBalance() - balance;
            return accountRepository.updateBalance(newBalance, accountId);
        } catch (AssignmentException e) {
            throw new AssignmentException(e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AssignmentException(e.getMessage(), ErrorCode.DATABASE);
        }
    }
}
