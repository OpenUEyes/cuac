package com.company.cuac.services;

import com.company.cuac.commands.CustomerAccountCommand;
import com.company.cuac.model.CustomerAccount;

import java.util.List;

public interface CustomerAccountService {

    List<CustomerAccount> listAll();

    CustomerAccount getById(String id);

    CustomerAccount saveOrUpdate(CustomerAccount account);

    CustomerAccount saveOrUpdateCustomerAccountCommand(CustomerAccountCommand command);

    void deleteById(String id);
}