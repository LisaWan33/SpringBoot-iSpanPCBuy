package com.ispan.pcbuy.model;

import lombok.Data;

@Data
public class OrderInfo {

    private Integer orderInfoId;
    private Integer orderId;
    private String name;
    private String tel;
    private String addr;
    private String payment;
    private String assemble;
}
