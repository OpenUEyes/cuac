package com.company.cuac.converters;

import com.company.cuac.commands.CustomerAccountCommand;
import com.company.cuac.model.CustomerAccount;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerAccountCommandToCustomerAccountConverter implements Converter<CustomerAccountCommand, CustomerAccount> {

    @Override
    public CustomerAccount convert(CustomerAccountCommand customerAccountCommand) {
        CustomerAccount customerAccount = new CustomerAccount();

        customerAccount.setId(customerAccountCommand.getId());
        customerAccount.setLogin(customerAccountCommand.getLogin());
        customerAccount.setPassword(customerAccountCommand.getPassword());
        customerAccount.setEmail(customerAccountCommand.getEmail());
        customerAccount.setImageURL(customerAccountCommand.getImageURL());
        return customerAccount;
    }
}