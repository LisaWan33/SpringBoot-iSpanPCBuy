package com.ispan.pcbuy.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String tel;

    @NotBlank
    private String addr;

    @Email
    private String email;
}
