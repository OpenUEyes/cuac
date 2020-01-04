package com.company.cuac.commands;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerAccountCommand {
    private String id;
    private String login;
    private String password;
    private String email;
    private String imageURL;
}