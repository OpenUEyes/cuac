package com.company.cuac.services;

import com.company.cuac.commands.CustomerAccountCommand;
import com.company.cuac.converters.CustomerAccountCommandToCustomerAccount;
import com.company.cuac.model.CustomerAccount;
import com.company.cuac.repositories.CustomerAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerAccountServiceImpl implements CustomerAccountService {

    private CustomerAccountCommandToCustomerAccount commandToModel;
    private CustomerAccountRepository customerAccountRepository;

    @Autowired
    public CustomerAccountServiceImpl(CustomerAccountCommandToCustomerAccount commandToModel,
                                      CustomerAccountRepository customerAccountRepository) {
        this.commandToModel = commandToModel;
        this.customerAccountRepository = customerAccountRepository;
    }

    @Override
    public List<CustomerAccount> listAll() {
        List<CustomerAccount> customerAccounts = new ArrayList<>();
        customerAccountRepository.findAll().forEach(customerAccounts::add);
        return customerAccounts;
    }

    @Override
    public CustomerAccount getById(String id) {
        return customerAccountRepository.findById(id).orElse(null);
    }

    @Override
    public CustomerAccount saveOrUpdate(CustomerAccount account) {
        customerAccountRepository.save(account);
        return account;
    }

    @Override
    public CustomerAccount saveOrUpdateCustomerAccountCommand(CustomerAccountCommand command) {
        return saveOrUpdate(commandToModel.convert(command));
    }

    @Override
    public void delete(String id) {
        customerAccountRepository.deleteById(id);
    }
}