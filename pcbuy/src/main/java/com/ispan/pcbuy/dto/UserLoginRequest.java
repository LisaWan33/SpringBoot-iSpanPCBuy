package com.ispan.pcbuy.dto;

import javax.validation.constraints.*;
import lombok.Data;

@Data
public class UserLoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
