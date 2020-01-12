package com.company.cuac.it;

import com.company.cuac.model.CustomerAccount;
import com.company.cuac.repositories.CustomerAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@Disabled("Redis db can't start at windows")
public class CustomerAccountIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CustomerAccountRepository repository;

    @Test
    public void saveOrUpdateWorksThroughAllLayers() throws Exception {
        CustomerAccount expectedAccount = CustomerAccount.builder()
                .id("1")
                .login("login")
                .password("password")
                .email("email@email")
                .imageURL("url")
                .build();

        this.mockMvc.perform(post("/account/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(expectedAccount)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(mapper.writeValueAsString(expectedAccount))));

        Optional<CustomerAccount> savedAccount = repository.findById(expectedAccount.getId());

        assertTrue(savedAccount.isPresent());
        assertThat(savedAccount.get()).isEqualTo(expectedAccount);
    }

    @Test
    public void getByIdWorksThroughAllLayers() throws Exception {
        final String accountId = "1";

        CustomerAccount account = CustomerAccount.builder()
                .id(accountId)
                .login("login")
                .password("password")
                .email("email@email")
                .imageURL("url")
                .build();

        repository.save(account);

        this.mockMvc.perform(get("/account/get/" + accountId)
                .content(accountId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(mapper.writeValueAsString(account))));

        assertTrue(repository.existsById(accountId));
    }

    @Test
    public void listAllWorksThroughAllLayers() throws Exception {
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

        repository.saveAll(customerAccountList);

        this.mockMvc.perform(get("/account/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(mapper.writeValueAsString(customerAccountList))));

        ArrayList<CustomerAccount> listAll = (ArrayList<CustomerAccount>) repository.findAll();
        assertTrue(listAll.containsAll(customerAccountList));
    }

    @Test
    public void deleteByIdWorksThroughAllLayers() throws Exception {
        final String accountId = "1";
        CustomerAccount account = CustomerAccount.builder()
                .id(accountId)
                .login("login")
                .password("password")
                .email("email@email")
                .imageURL("url")
                .build();

        repository.save(account);
        assertTrue(repository.existsById(accountId));

        this.mockMvc.perform(post("/account/delete/" + accountId)
                .content(accountId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/account/list"));

        assertFalse(repository.existsById(accountId));
    }
}
