package com.company.cuac.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerAccountCommand {

    @NotEmpty
    private String id;

    @NotEmpty
    private String login;

    @NotEmpty
    private String password;

    @Email
    private String email;

    @NotEmpty
    private String imageURL;
}