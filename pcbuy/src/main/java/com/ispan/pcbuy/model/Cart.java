package com.ispan.pcbuy.model;

import lombok.Data;

@Data
public class Cart {

    private Integer cartId;
    private Integer userId;
    private Integer productId;

    private String  productName;
    private Integer price;
    private String  description;
    private String  imageUrl;
}
