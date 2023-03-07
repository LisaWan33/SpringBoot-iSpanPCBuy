package com.ispan.pcbuy.dao;

import com.ispan.pcbuy.constant.ProductCategory;
import com.ispan.pcbuy.dto.ProductQueryParams;
import com.ispan.pcbuy.dto.ProductRequest;
import com.ispan.pcbuy.model.Product;

import java.util.List;

public interface ProductDao {

    Integer countProduct(ProductQueryParams productQueryParams);

    List<Product> getProducts(ProductQueryParams productQueryParams);

    List<Product> getProductsFromCategory(String category);

    List<Product> getPowerByFilter(ProductCategory category, Integer totalWatt);

    List<Product> getMbByFilter(ProductCategory category, String filterI);

    List<Product> getDramByFilter(ProductCategory category, String filterI);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void updateStock(Integer productId, Integer stock);

    void  deleteProductById(Integer productId);
}
