package com.company.cuac.converters;

import com.company.cuac.commands.CustomerAccountCommand;
import com.company.cuac.model.CustomerAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerAccountCustomerAccountCommandConverter {

    CustomerAccount commandToModel(CustomerAccountCommand command);
}
