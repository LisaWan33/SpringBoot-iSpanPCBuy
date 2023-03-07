package com.ispan.pcbuy.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderStateRequest {

    @NotNull
    private Integer orderId;

    @NotNull
    private String state;
}
