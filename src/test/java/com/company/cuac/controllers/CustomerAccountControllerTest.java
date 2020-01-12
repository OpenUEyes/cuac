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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                .content("" + accountId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(mapper.writeValueAsString(account))));
    }

    @Test
    public void listAll() throws Exception {
        List<CustomerAccount> customerAccountList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            CustomerAccount account = CustomerAccount.builder()
                    .id("" + i)
                    .login("login" + i)
                    .password("password" + i)
                    .email("email" + i + "@mail.com")
                    .imageURL("url" + i)
                    .build();
            customerAccountList.add(account);
        }

        when(customerAccountService.listAll()).thenReturn(customerAccountList);

        this.mockMvc.perform(get("/account/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(mapper.writeValueAsString(customerAccountList))));
    }

    @Test
    public void deleteById() throws Exception {
        doNothing().when(customerAccountService).deleteById(anyString());

        final int accountId = 1;
        this.mockMvc.perform(post("/account/delete/" + accountId)
                .content("" + accountId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/account/list"));
    }
}