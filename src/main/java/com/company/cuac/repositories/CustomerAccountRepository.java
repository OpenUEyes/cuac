package com.company.cuac.repositories;

import com.company.cuac.model.CustomerAccount;
import org.springframework.data.repository.CrudRepository;

public interface CustomerAccountRepository extends CrudRepository<CustomerAccount, String> {
}