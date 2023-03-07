package com.ispan.pcbuy.dto;

import com.ispan.pcbuy.constant.ProductCategory;
import lombok.Data;

@Data
public class ProductRequest {

    /*
    @NotNull
    private String productName;

    @NotNull
    private ProductCategory category;

    @NotNull
    private String imageUrl;

    @NotNull
    private Integer price;

    @NotNull
    private Integer stock;

    private String description;
    */
    private String productName;
    private ProductCategory category;
    private String brand;
    private String series;
    private Integer watt;
    private String  socket;
    private Integer score;
    private String size;
    private Double coolerLength;
    private Double coolerHeight;
    private Double gpuLength;
    private Integer capacity;
    private Boolean state;
    private String description;
    private String imageUrl;
    private Integer stock;
    private Integer price;
}
