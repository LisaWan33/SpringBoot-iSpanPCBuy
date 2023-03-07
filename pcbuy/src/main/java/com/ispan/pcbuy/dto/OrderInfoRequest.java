package com.ispan.pcbuy.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderInfoRequest {

    @NotNull
    private String name;

    @NotNull
    private String tel;

    @NotNull
    private String addr;

    @NotNull
    private String payment;

    @NotNull
    private String assemble;
}
