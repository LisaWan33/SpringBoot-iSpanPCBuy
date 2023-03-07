package com.ispan.pcbuy.model;

import com.ispan.pcbuy.constant.ProductCategory;
import lombok.Data;
import java.util.Date;

@Data
public class Product {

   private Integer productId;
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
   private Date createdDate;
   private Date lastModifiedDate;
}
