package com.ispan.pcbuy.service;

import com.ispan.pcbuy.constant.ProductCategory;
import com.ispan.pcbuy.dto.ProductQueryParams;
import com.ispan.pcbuy.dto.ProductRequest;
import com.ispan.pcbuy.model.Product;

import java.util.List;

public interface ProductService {

    Integer countProduct(ProductQueryParams productQueryParams);

    List<Product> getProducts(ProductQueryParams productQueryParams);

    List<Product> getProductsFromCategory(String category);

//    List<Product> getMbBySocket(String socket);

    List<Product> getCategoryByFilter(ProductCategory category, String filterI, String filterII, String filterIII);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void  updateProduct(Integer productId, ProductRequest productRequest);

    void  deleteProductById(Integer productId);
}
