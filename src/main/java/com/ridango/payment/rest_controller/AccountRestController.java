package com.ridango.payment.rest_controller;

import com.ridango.payment.dao_impl.AccountService;
import com.ridango.payment.exceptions.AssignmentException;
import com.ridango.payment.model.Account;
import com.ridango.payment.model.AccountDto;
import com.ridango.payment.model.ResponseError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccountRestController {
    private static final Logger logger = LogManager.getLogger(AccountRestController.class);

    @Autowired
    AccountService accountService;

    @GetMapping("/account/{accountId}")
    @ResponseBody
    public ResponseEntity<AccountDto> getAccount(@PathVariable BigInteger accountId) {
        try {
            Account account = accountService.getAccountById(accountId);
            AccountDto accountDto = new AccountDto(account.getName(),account.getBalance());
            return new ResponseEntity<>(accountDto, HttpStatus.OK);
        } catch (AssignmentException cce) {
            return new ResponseEntity(new ResponseError(cce.getErrorCode().getCode(), cce.getErrorCode().getDescription()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseError(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/account")
    @ResponseBody
    public ResponseEntity<Account> addAccount(@RequestBody AccountDto accountDto) {
        try {
            Account account = new Account(accountDto.getName(),accountDto.getBalance());
            Account createdAccount = accountService.saveAccount(account);
            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
        } catch (AssignmentException cce) {
            return new ResponseEntity(new ResponseError(cce.getErrorCode().getCode(), cce.getErrorCode().getDescription()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseError(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
