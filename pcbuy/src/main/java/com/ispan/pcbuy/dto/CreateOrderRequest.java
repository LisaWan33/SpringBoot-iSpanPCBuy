package com.ispan.pcbuy.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class CreateOrderRequest {

    @NotEmpty
    private List<BuyItem> buyItemList;
}
