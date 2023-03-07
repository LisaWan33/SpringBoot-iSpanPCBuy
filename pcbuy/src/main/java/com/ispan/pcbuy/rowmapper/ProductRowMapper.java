package com.ispan.pcbuy.rowmapper;

import com.ispan.pcbuy.constant.ProductCategory;
import com.ispan.pcbuy.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Product product = new Product();

        product.setProductId(resultSet.getInt("product_id"));
        product.setProductName(resultSet.getString("product_name"));

        String categoryString = resultSet.getString("category");
        ProductCategory productCategory = ProductCategory.valueOf(categoryString);
        product.setCategory(productCategory);

//        product.setCategory(ProductCategory.valueOf(resultSet.getString("category")));

        product.setBrand(resultSet.getString("brand"));
        product.setSeries(resultSet.getString("series"));
        product.setWatt(resultSet.getInt("watt"));
        product.setSocket(resultSet.getString("socket"));
        product.setScore(resultSet.getInt("score"));
        product.setSize(resultSet.getString("size"));
        product.setCoolerLength(resultSet.getDouble("cooler_length"));
        product.setCoolerHeight(resultSet.getDouble("cooler_height"));
        product.setGpuLength(resultSet.getDouble("gpu_length"));
        product.setCapacity(resultSet.getInt("capacity"));
        product.setState(resultSet.getBoolean("state"));
        product.setImageUrl(resultSet.getString("image_url"));
        product.setPrice(resultSet.getInt("price"));
        product.setStock(resultSet.getInt("stock"));
        product.setDescription(resultSet.getString("description"));
        product.setCreatedDate(resultSet.getTimestamp("created_date"));
        product.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));

        return product;
    }
}
