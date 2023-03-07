package com.ispan.pcbuy.service.impl;

import com.ispan.pcbuy.constant.ProductCategory;
import com.ispan.pcbuy.dao.ProductDao;
import com.ispan.pcbuy.dto.ProductQueryParams;
import com.ispan.pcbuy.dto.ProductRequest;
import com.ispan.pcbuy.model.Product;
import com.ispan.pcbuy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        return productDao.countProduct(productQueryParams);
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        return productDao.getProducts(productQueryParams);
    }

    @Override
    public List<Product> getProductsFromCategory(String category) {
        return productDao.getProductsFromCategory(category);
    }

    @Override
    public List<Product> getCategoryByFilter(ProductCategory category, String filterI, String filterII, String filterIII) {
//        System.out.println("我是Service.getCategoryByFilter()" + category + " " + filterI);
        if (filterI == null && filterII == null && filterIII == null){
            return productDao.getProductsFromCategory(category.name());
        }
        else if (category.name().equals("MB")) {
            if(filterI != null) {
                return productDao.getMbByFilter(category, filterI);
            }else if (filterI == null){
                return productDao.getProductsFromCategory(category.name());
            }else {
                return null;
            }
        }
        else if (category.name().equals("DRAM")){
            if (filterI.contains("DDR4")) {
                System.out.println("equals=DDR4, filterI=" + filterI);
                return productDao.getDramByFilter(category, "DDR4");
            }else if (filterI.contains("DDR5")){
                return productDao.getDramByFilter(category, "DDR5");
            }else if (filterI == null) {
                return productDao.getProductsFromCategory(category.name());
            }else {
                return productDao.getDramByFilter(category, "DDR4");
            }
        }else if (category.name().equals("POWER")){
            if (filterI == null) {
                return productDao.getProductsFromCategory(category.name());
            }else if(Integer.parseInt(filterI) > 0){
                int totalWatt = Integer.parseInt(filterI);
                return productDao.getPowerByFilter(category, totalWatt);
            }
            else {
                return null;
            }
        }else if (category.name().equals("GPU")){
            if(filterI == null) {
                return productDao.getProductsFromCategory(category.name());
            }else{
                return null;
            }
        } else if (category.name().equals("STORAGE")){
            if(filterI == null) {
                return productDao.getProductsFromCategory(category.name());
            }else{
                return null;
            }
        } else if (category.name().equals("COOLER")){
            if(filterI == null) {
                return productDao.getProductsFromCategory(category.name());
            }else{
                return null;
            }
        }
        else {
            return null;
        }
    }
    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }
}
