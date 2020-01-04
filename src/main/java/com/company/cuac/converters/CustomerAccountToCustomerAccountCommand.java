package com.company.cuac.converters;

import com.company.cuac.commands.CustomerAccountCommand;
import com.company.cuac.model.CustomerAccount;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerAccountToCustomerAccountCommand implements Converter<CustomerAccount, CustomerAccountCommand> {

    @Override
    public CustomerAccountCommand convert(CustomerAccount customerAccount) {
        CustomerAccountCommand customerAccountCommand = new CustomerAccountCommand();
        customerAccountCommand.setId(customerAccount.getId());
        customerAccountCommand.setLogin(customerAccount.getLogin());
        customerAccountCommand.setPassword(customerAccount.getPassword());
        customerAccountCommand.setEmail(customerAccount.getEmail());
        customerAccountCommand.setImageURL(customerAccount.getImageURL());
        return customerAccountCommand;
    }
}