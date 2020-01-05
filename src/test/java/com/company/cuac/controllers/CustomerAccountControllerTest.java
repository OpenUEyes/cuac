package com.company.cuac.controllers;

import com.company.cuac.commands.CustomerAccountCommand;
import com.company.cuac.model.CustomerAccount;
import com.company.cuac.services.CustomerAccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = RestTemplate.class)
class CustomerAccountControllerTest {

    @Autowired
    private RestTemplate restTemplate;

    @Mock
    private CustomerAccountService customerAccountService;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
//
        final CustomerAccount accountCompleteFirst = CustomerAccount.builder()
                .id("1")
                .login("login1")
                .password("password1")
                .email("email@1")
                .imageURL("url1")
                .build();
        final CustomerAccount accountCompleteSecond = CustomerAccount.builder()
                .id("2")
                .login("login2")
                .password("password2")
                .email("email@2")
                .imageURL("url2")
                .build();
//
        List<CustomerAccount> customerAccounts = new ArrayList<>();
        customerAccounts.add(accountCompleteFirst);
        customerAccounts.add(accountCompleteSecond);

        final CustomerAccountCommand accountCommandWithoutId = CustomerAccountCommand.builder()
                .login("login")
                .password("password")
                .email("email@")
                .imageURL("url")
                .build();

//        when(customerAccountService.listAll()).thenReturn(customerAccounts);
        when(customerAccountService.getById(anyString())).thenReturn(accountCompleteFirst);
    }

    @Test
    public void save() throws URISyntaxException, JsonProcessingException {
        CustomerAccount expectedSavedAccount = CustomerAccount.builder()
                .id("1")
                .login("login")
                .password("password")
                .email("email@")
                .imageURL("url")
                .build();

        when(customerAccountService.saveOrUpdateCustomerAccountCommand(any())).thenReturn(expectedSavedAccount);
//        CustomerAccountCommand accountCommand = CustomerAccountCommand.builder()
//                .id("1")
//                .login("login")
//                .password("password")
//                .email("email@")
//                .imageURL("url")
//                .build();
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8080/account/save")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(expectedSavedAccount))
                );
        mockServer.verify();

        assertEquals(expectedSavedAccount, customerAccountService.getById(expectedSavedAccount.getId()));
    }
}