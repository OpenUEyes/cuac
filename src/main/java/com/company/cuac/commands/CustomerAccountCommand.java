package com.company.cuac.commands;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerAccountCommand {
    private String id;
    private String login;
    private String password;
    private String email;
    private String imageURL;
}