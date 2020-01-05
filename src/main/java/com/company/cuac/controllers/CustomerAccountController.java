package com.company.cuac.controllers;

import com.company.cuac.commands.CustomerAccountCommand;
import com.company.cuac.model.CustomerAccount;
import com.company.cuac.services.CustomerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class CustomerAccountController {

    private CustomerAccountService customerAccountService;

    @Autowired
    public CustomerAccountController(CustomerAccountService customerAccountService) {
        this.customerAccountService = customerAccountService;
    }

    @PostMapping({"/save", "/update"})
    public CustomerAccount saveOrUpdate(@RequestBody CustomerAccountCommand customerAccountCommand) {
        return customerAccountService.saveOrUpdateCustomerAccountCommand(customerAccountCommand);
    }

    @GetMapping("/list")
    public List<CustomerAccount> listAll() {
        return customerAccountService.listAll();
    }

    @GetMapping("/get/{id}")
    public CustomerAccount getById(@PathVariable String id) {
        return customerAccountService.getById(id);
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        customerAccountService.delete(id);
        return "redirect:/account/list";
    }
}