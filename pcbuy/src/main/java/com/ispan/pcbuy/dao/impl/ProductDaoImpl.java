package com.ispan.pcbuy.dao.impl;

import com.ispan.pcbuy.constant.ProductCategory;
import com.ispan.pcbuy.dao.ProductDao;
import com.ispan.pcbuy.dto.ProductQueryParams;
import com.ispan.pcbuy.dto.ProductRequest;
import com.ispan.pcbuy.model.Product;
import com.ispan.pcbuy.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        String sql = "SELECT count(*) FROM product WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        sql = addFilteringSql(sql, map, productQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return total;
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        String sql = "SELECT * FROM product WHERE 1=1 ";

        Map<String, Object> map = new HashMap<>();

        sql = addFilteringSql(sql, map, productQueryParams);

        //排序
        sql = sql + " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();

        //分頁
        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit", productQueryParams.getLimit());
        map.put("offset", productQueryParams.getOffset());

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return productList;
    }

    @Override //抓出該Category所有的品項
    public List<Product> getProductsFromCategory(String category) {
        String sql = "SELECT * FROM product WHERE category = :category ORDER BY product_name";
        Map<String, Object> map = new HashMap<>();
        map.put("category", category);
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        return productList;
    }

    @Override //藉由CPU腳位篩選並列出符合的主機板
    public List<Product> getMbByFilter(ProductCategory category, String filterI) {
        System.out.println("我是Dao.getMbByFilter" + category + " " + filterI);
        String sql = "SELECT * FROM product WHERE category = :category AND socket = :socket ORDER BY product_name";
        Map<String, Object> map = new HashMap<>();
        map.put("category", category.name());
        map.put("socket", filterI);
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        return productList;
    }

    @Override //藉由主機板晶片組篩選並列出符合的記憶體
    public List<Product> getDramByFilter(ProductCategory category, String filterI) {
        String sql = "SELECT * FROM product WHERE category = :category AND socket = :socket ORDER BY product_name";
        Map<String, Object> map = new HashMap<>();
        map.put("category", category.name());
        map.put("socket", filterI);
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        return productList;
    }

    @Override //藉由目前總瓦數篩選並列出符合的電源供應器
    public List<Product> getPowerByFilter(ProductCategory category, Integer totalWatt) {
        String sql = "SELECT * FROM product WHERE category = :category AND watt >= :totalWatt ORDER BY product_name";
        Map<String, Object> map = new HashMap<>();
        map.put("category", category.name());
        map.put("totalWatt", totalWatt);
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        return productList;
    }

    @Override //藉由ID搜尋產品
    public Product getProductById(Integer productId) {
         String sql = "SELECT * FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if(productList.size() > 0){
            return productList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql= "INSERT INTO product (product_name, category, brand, series, watt, socket, score, size, cooler_length, cooler_height, gpu_length, capacity, state, description, image_url, stock, price, created_date, last_modified_date) " +
            "VALUES (:productName, :category, :brand, :series, :watt, :socket, :score, :size, :coolerLength, :coolerHeight, :gpuLength, :capacity, :state, :description, :imageUrl, :stock, :price, :createDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("brand", productRequest.getBrand());
        map.put("series", productRequest.getSeries());
        map.put("watt", productRequest.getWatt());
        map.put("socket", productRequest.getSocket());
        map.put("score", productRequest.getScore());
        map.put("size", productRequest.getSize());
        map.put("coolerLength", productRequest.getCoolerLength());
        map.put("coolerHeight", productRequest.getCoolerHeight());
        map.put("gpuLength", productRequest.getGpuLength());
        map.put("capacity", productRequest.getCapacity());
        map.put("state", productRequest.getState());
        map.put("description", productRequest.getDescription());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("stock", productRequest.getStock());
        map.put("price", productRequest.getPrice());

        Date now = new Date();
        map.put("createDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId = keyHolder.getKey().intValue();

        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql= "UPDATE product SET product_name = :productName, "            +
                                       "category = :category, "                   +
                                       "brand = :brand, "                         +
                                       "series = :series, "                       +
                                       "watt = :watt, "                           +
                                       "socket = :socket, "                       +
                                       "score = :score, "                         +
                                       "`size`= :size, "                          +
                                       "cooler_length = :coolerLength, "          +
                                       "cooler_height = :coolerHeight, "          +
                                       "gpu_length = :gpuLength, "                +
                                       "capacity = :capacity, "                   +
                                       "`state` = :state, "                       +
                                       "description = :description, "             +
                                       "image_url = :imageUrl, "                  +
                                       "stock = :stock, "                         +
                                       "price = :price, "                         +
                                       "last_modified_date = :lastModifiedDate "  +
                    "WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("brand", productRequest.getBrand());
        map.put("series", productRequest.getSeries());
        map.put("watt", productRequest.getWatt());
        map.put("socket", productRequest.getSocket());
        map.put("score", productRequest.getScore());
        map.put("size", productRequest.getSize());
        map.put("coolerLength", productRequest.getCoolerLength());
        map.put("coolerHeight", productRequest.getCoolerHeight());
        map.put("gpuLength", productRequest.getGpuLength());
        map.put("capacity", productRequest.getCapacity());
        map.put("state", productRequest.getState());
        map.put("description", productRequest.getDescription());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("stock", productRequest.getStock());
        map.put("price", productRequest.getPrice());
        map.put("lastModifiedDate", new Date());
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void updateStock(Integer productId, Integer stock) {
        String sql = "UPDATE product SET stock = :stock, last_modified_date = :lastModifiedDate " +
                     "WHERE product_id = :productId ";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("stock", stock);
        map.put("lastModifiedDate", new Date());
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }

    private String addFilteringSql(String sql, Map<String, Object> map, ProductQueryParams productQueryParams) {

        //查詢條件
        if (productQueryParams.getCategory() != null) {
            sql = sql + " AND category = :category";
            map.put("category", productQueryParams.getCategory().name());
        }

        if (productQueryParams.getSearch() != null) {
            sql = sql + " AND product_name LIKE :search";
            map.put("search", "%" + productQueryParams.getSearch() + "%");
        }

        return sql;
    }
}
