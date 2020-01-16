package com.company.cuac.controllers;

import com.company.cuac.commands.CustomerAccountCommand;
import com.company.cuac.model.CustomerAccount;
import com.company.cuac.services.CustomerAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class CustomerAccountController {

    private final CustomerAccountService customerAccountService;

    @ResponseBody
    @PostMapping({"/save", "/update"})
    public CustomerAccount saveOrUpdate(@Valid @RequestBody CustomerAccountCommand customerAccountCommand) {
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
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        customerAccountService.deleteById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/account/list");
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}