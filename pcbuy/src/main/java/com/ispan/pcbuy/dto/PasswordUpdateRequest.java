package com.ispan.pcbuy.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordUpdateRequest {

    @NotBlank
    private String passwordOld;

    @NotBlank
    private String passwordNew;
}
