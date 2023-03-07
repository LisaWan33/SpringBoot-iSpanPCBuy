package com.ispan.pcbuy.rowmapper;

import com.ispan.pcbuy.model.Cart;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartRowMapper implements RowMapper<Cart> {

    @Override
    public Cart mapRow(ResultSet resultSet, int i) throws SQLException {
        Cart cart = new Cart();
        cart.setCartId(resultSet.getInt("cart_id"));
        cart.setUserId(resultSet.getInt("user_id"));
        cart.setProductId(resultSet.getInt("product_id"));

        //額外擴充去接收JOIN後的數據
        cart.setProductName(resultSet.getString("product_name"));
        cart.setPrice(resultSet.getInt("price"));
        cart.setDescription(resultSet.getString("description"));
        cart.setImageUrl(resultSet.getString("image_url"));

        return cart;
    }
}
