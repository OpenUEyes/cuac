package com.company.cuac.services;

import com.company.cuac.commands.CustomerAccountCommand;
import com.company.cuac.converters.CustomerAccountCustomerAccountCommandConverter;
import com.company.cuac.converters.CustomerAccountCustomerAccountCommandConverterImpl;
import com.company.cuac.model.CustomerAccount;
import com.company.cuac.repositories.CustomerAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerAccountServiceImplTest {

    @Mock
    private CustomerAccountRepository repository;

    @Test
    void listAll() {
        List<CustomerAccount> expectedCustomerAccountList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            CustomerAccount account = CustomerAccount.builder()
                    .id("" + i)
                    .login("login" + i)
                    .password("password" + i)
                    .email("email" + i + "@mail.com")
                    .imageURL("url" + i)
                    .build();
            expectedCustomerAccountList.add(account);
        }

        lenient().when(repository.findAll()).thenReturn(expectedCustomerAccountList);

        CustomerAccountServiceImpl service = new CustomerAccountServiceImpl(null, repository);
        assertEquals(expectedCustomerAccountList, service.listAll());
    }

    @Test
    void getById() {
        final int accountId = 1;
        CustomerAccount expected = CustomerAccount.builder()
                .id("" + accountId)
                .login("login")
                .password("password")
                .email("email@email")
                .imageURL("url")
                .build();

        when(repository.findById(any())).thenReturn(Optional.of(expected));

        CustomerAccountServiceImpl service = new CustomerAccountServiceImpl(null, repository);
        assertEquals(expected, service.getById("" + accountId));
    }

    @Test
    void saveOrUpdate() {
        CustomerAccount expectedAccount = CustomerAccount.builder()
                .id("1")
                .login("login")
                .password("password")
                .email("email@email")
                .imageURL("url")
                .build();

        lenient().when(repository.save(any(CustomerAccount.class))).thenReturn(expectedAccount);

        CustomerAccountService service = new CustomerAccountServiceImpl(null, repository);
        assertEquals(expectedAccount, service.saveOrUpdate(expectedAccount));
    }

    @Test
    void saveOrUpdateCustomerAccountCommand() {
        CustomerAccountCustomerAccountCommandConverter mapper = new CustomerAccountCustomerAccountCommandConverterImpl();
        CustomerAccount expectedAccount = CustomerAccount.builder()
                .id("1")
                .login("login")
                .password("password")
                .email("email@email")
                .imageURL("url")
                .build();
        CustomerAccountCommand inputCommand = CustomerAccountCommand.builder()
                .id("1")
                .login("login")
                .password("password")
                .email("email@email")
                .imageURL("url")
                .build();

        lenient().when(repository.save(any(CustomerAccount.class))).thenReturn(expectedAccount);

        CustomerAccountService service = new CustomerAccountServiceImpl(mapper, repository);
        assertEquals(expectedAccount, service.saveOrUpdateCustomerAccountCommand(inputCommand));
    }

    @Test
    void deleteById() {
        doNothing().when(repository).deleteById(anyString());

        final String accountId = "1";
        CustomerAccountService service = new CustomerAccountServiceImpl(null, repository);
        service.deleteById(accountId);
    }
}