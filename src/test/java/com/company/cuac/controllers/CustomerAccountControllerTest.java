package com.company.cuac.controllers;

import com.company.cuac.model.CustomerAccount;
import com.company.cuac.services.CustomerAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerAccountController.class)
class CustomerAccountControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerAccountService customerAccountService;

    @Test
    public void saveOrUpdate() throws Exception {
        CustomerAccount savedAccount = CustomerAccount.builder()
                .id("1")
                .login("login")
                .password("password")
                .email("email@email")
                .imageURL("url")
                .build();
        when(customerAccountService.saveOrUpdateCustomerAccountCommand(any())).thenReturn(savedAccount);

        this.mockMvc.perform(post("/account/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(savedAccount)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(mapper.writeValueAsString(savedAccount))));
    }

    @Test
    public void getById() throws Exception {
        CustomerAccount account = CustomerAccount.builder()
                .id("1")
                .login("login")
                .password("password")
                .email("email@email")
                .imageURL("url")
                .build();

        when(customerAccountService.getById(any())).thenReturn(account);
        final int accountId = 1;

        this.mockMvc.perform(get("/account/get/" + accountId)
                .content("1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(mapper.writeValueAsString(account))));
    }
}