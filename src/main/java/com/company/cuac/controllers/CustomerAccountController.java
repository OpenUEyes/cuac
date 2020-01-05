package com.company.cuac.controllers;

import com.company.cuac.commands.CustomerAccountCommand;
import com.company.cuac.model.CustomerAccount;
import com.company.cuac.services.CustomerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/account")
public class CustomerAccountController {

    private CustomerAccountService customerAccountService;

    @Autowired
    public CustomerAccountController(CustomerAccountService customerAccountService) {
        this.customerAccountService = customerAccountService;
    }

    @ResponseBody
    @PostMapping({"/save", "/update"})
    public CustomerAccount saveOrUpdate(@RequestBody CustomerAccountCommand customerAccountCommand) {
        return customerAccountService.saveOrUpdateCustomerAccountCommand(customerAccountCommand);
    }

    @ResponseBody
    @GetMapping("/list")
    public List<CustomerAccount> listAll() {
        return customerAccountService.listAll();
    }

    @ResponseBody
    @GetMapping("/get/{id}")
    public CustomerAccount getById(@PathVariable String id) {
        return customerAccountService.getById(id);
    }

    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable String id) {
        customerAccountService.deleteById(id);
        return "redirect:/account/list";
    }
}