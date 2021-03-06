package com.company.cuac.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@RedisHash("account")
public class CustomerAccount {
    @Id
    private String id;
    private String login;
    private String password;
    private String email;
    private String imageURL;
}