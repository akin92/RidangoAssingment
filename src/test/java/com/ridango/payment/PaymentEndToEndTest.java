package com.ridango.payment;

import com.ridango.payment.controller.PaymentController;
import com.ridango.payment.dao_impl.AccountService;
import com.ridango.payment.exceptions.AssignmentException;
import com.ridango.payment.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = PaymentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
@AutoConfigureMockMvc
public class PaymentEndToEndTest {
    @LocalServerPort
    private int port;

    @Autowired
    AccountService accountService;

    @Autowired
    PaymentController paymentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        assertThat(accountService).isNotNull();
        assertThat(paymentController).isNotNull();
    }

    @Test
    void testAddAccount() throws URISyntaxException, AssignmentException {
        final String baseUrl = "http://localhost:" + port + "/api/account";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        AccountDto accountDto = new AccountDto("Akin", 200.00);
        HttpEntity<AccountDto> request = new HttpEntity<>(accountDto,headers);
        ResponseEntity<Account> result =this.restTemplate.exchange(uri,
                HttpMethod.POST,
                request,
                Account.class);
        //Verify request succeed
        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        Account createdAccount = accountService.getAccountById(result.getBody().getId());
        assertThat(createdAccount).isNotNull();
    }

    @Test
    void testGetAccount() throws AssignmentException, URISyntaxException {
        Account savedAccount = accountService.saveAccount(new Account("Akin",500.00));
        final String baseUrl = "http://localhost:" + port + "/api/account/" + savedAccount.getId();
        URI uri = new URI(baseUrl);
        ResponseEntity<AccountDto> result = this.restTemplate.getForEntity(uri, AccountDto.class);
        //Verify request succeed
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getName()).isEqualTo(savedAccount.getName());
    }
    @Test
    void testGetAccountNotExist() throws AssignmentException, URISyntaxException{
        final String baseUrl = "http://localhost:" + port + "/api/account/" + 100;
        URI uri = new URI(baseUrl);
        ResponseEntity<ResponseError> result = this.restTemplate.getForEntity(uri, ResponseError.class);
        //Verify request succeed
        assertThat(result.getStatusCodeValue()).isEqualTo(409);
        assertThat(result.getBody().getDesc()).isEqualTo("Account does not exist !!!");
    }

    @Test
    void testAddPaymentRest() throws AssignmentException, URISyntaxException {
        final String baseUrl = "http://localhost:" + port + "/api/payment";
        Account savedAccount1 = accountService.saveAccount(new Account("John",500.00));
        Account savedAccount2 = accountService.saveAccount(new Account("Paul",500.00));
        PaymentDto paymentDto = new PaymentDto(savedAccount1.getId(),savedAccount2.getId(),100.00);
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PaymentDto> request = new HttpEntity<>(paymentDto,headers);
        ResponseEntity<PaymentDto> result =this.restTemplate.exchange(uri,
                HttpMethod.POST,
                request,
                PaymentDto.class);
        //Verify request succeed
        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getBody()).isNotNull();
        List<Payment> paymentList = paymentController.getPaymentBySenderAccountId(savedAccount1.getId());
        assertThat(paymentList.isEmpty()).isFalse();
        Account withdrawedAccount = accountService.getAccountById(savedAccount1.getId());
        Account depositedAccount = accountService.getAccountById(savedAccount2.getId());
        assertThat(withdrawedAccount.getBalance()).isEqualTo(400);
        assertThat(depositedAccount.getBalance()).isEqualTo(600);

    }

    /** Add More Test Cases According To Rest EndPoints !!!!!!!!!!!1
     *
     *
     * */
}
