package com.company.cuac.services;

import com.company.cuac.commands.CustomerAccountCommand;
import com.company.cuac.converters.CustomerAccountCustomerAccountCommandConverter;
import com.company.cuac.model.CustomerAccount;
import com.company.cuac.repositories.CustomerAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerAccountServiceImpl implements CustomerAccountService {

    private final CustomerAccountCustomerAccountCommandConverter mapper;
    private final CustomerAccountRepository customerAccountRepository;

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
        return customerAccountRepository.save(account);
    }

    @Override
    public CustomerAccount saveOrUpdateCustomerAccountCommand(CustomerAccountCommand command) {
        return saveOrUpdate(mapper.commandToModel(command));
    }

    @Override
    public void deleteById(String id) {
        customerAccountRepository.deleteById(id);
    }
}