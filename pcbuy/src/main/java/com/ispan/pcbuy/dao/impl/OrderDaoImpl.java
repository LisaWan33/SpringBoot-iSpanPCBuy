package com.ispan.pcbuy.dao.impl;

import com.ispan.pcbuy.dao.OrderDao;

import com.ispan.pcbuy.dto.CreateCartRequest;
import com.ispan.pcbuy.dto.OrderInfoRequest;
import com.ispan.pcbuy.model.Cart;
import com.ispan.pcbuy.model.Order;
import com.ispan.pcbuy.model.OrderInfo;
import com.ispan.pcbuy.model.OrderItem;
import com.ispan.pcbuy.rowmapper.CartRowMapper;
import com.ispan.pcbuy.rowmapper.OrderInfoRowMapper;
import com.ispan.pcbuy.rowmapper.OrderItemRowMapper;
import com.ispan.pcbuy.rowmapper.OrderRowMapper;
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
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO `order` (user_id, total_amount, `state`, created_date, last_modified_date)" +
                "VALUES (:userId, :totalAmount, :state, :createDate, :lastModifiedDate)";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalAmount", totalAmount);
        map.put("state", "尚未結帳");

        Date now = new Date();
        map.put("createDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int orderId = keyHolder.getKey().intValue();

        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
         String sql = "INSERT INTO order_item (order_id, product_id, quantity, amount)" +
                 "VALUES (:orderId, :productId, :quantity, :amount)";

         MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];

         for (int i = 0 ; i < orderItemList.size(); i++){
             OrderItem orderItem = orderItemList.get(i);

             parameterSources[i] = new MapSqlParameterSource();
             parameterSources[i].addValue("orderId", orderId);
             parameterSources[i].addValue("productId", orderItem.getProductId());
             parameterSources[i].addValue("quantity", orderItem.getQuantity());
             parameterSources[i].addValue("amount", orderItem.getAmount());
         }

         namedParameterJdbcTemplate.batchUpdate(sql,parameterSources);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT order_id, user_id, total_amount, `state`, created_date, last_modified_date " +
                     "FROM `order` WHERE order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        if(orderList.size() > 0) {
            return orderList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        String sql = "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, p.product_name, p.image_url " +
                     "FROM order_item as oi " +
                     "INNER JOIN product as p ON oi.product_id = p.product_id " +
                     "WHERE oi.order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<OrderItem> orderItemList =namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());
        return orderItemList;
    }

    @Override
    public void createCart(Integer userId, List<Cart> cartList) {
        String sql = "INSERT INTO cart (user_id, product_id)" +
                "VALUES (:userId, :productId)";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[cartList.size()];

        for (int i = 0 ; i < cartList.size(); i++){
            Cart cart = cartList.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("userId", userId);
            parameterSources[i].addValue("productId", cart.getProductId());
        }
        namedParameterJdbcTemplate.batchUpdate(sql,parameterSources);
    }

    @Override
    public List<Cart> getCart(Integer userId) {
        String sql = "SELECT c.cart_id, c.user_id, c.product_id, p.product_name, p.price, p.description, p.image_url " +
                "FROM cart as c " +
                "LEFT JOIN product as p ON c.product_id = p.product_id " +
                "WHERE c.user_id = :userId";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        List<Cart> cartList =namedParameterJdbcTemplate.query(sql, map, new CartRowMapper());
        return cartList;
    }

    @Override
    public void clearCart(Integer userId) {
        String sql = "DELETE FROM cart WHERE user_id = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public List<Order> getOrderByUserId(Integer userId) {
        String sql = "SELECT order_id, user_id, total_amount, `state`, created_date, last_modified_date " +
                "FROM `order` WHERE user_id = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        if(orderList.size() > 0) {
            return orderList;
        } else {
            return null;
        }
    }

    @Override
    public List<Order> getOrderAll() {
        String sql = "SELECT order_id, user_id, total_amount, `state`, created_date, last_modified_date " +
                "FROM `order`";

        Map<String, Object> map = new HashMap<>();

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        if(orderList.size() > 0) {
            return orderList;
        } else {
            return null;
        }
    }

    @Override
    public void updateOrders(Integer orderId, String state) {
        String sql = "UPDATE `order` SET `state` = :state, last_modified_date = :lastModifiedDate " +
                "WHERE order_id = :orderId ";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("state", state);
        map.put("lastModifiedDate", new Date());
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void createOrderInfo(Integer orderId, OrderInfoRequest orderInfoRequest) {
        String sql = "INSERT INTO order_info (order_id, `name`, tel, addr, payment, assemble)" +
                "VALUES (:orderId, :name, :tel, :addr, :payment, :assemble)";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("name", orderInfoRequest.getName());
        map.put("tel", orderInfoRequest.getTel());
        map.put("addr", orderInfoRequest.getAddr());
        map.put("payment", orderInfoRequest.getPayment());
        map.put("assemble", orderInfoRequest.getAssemble());

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
    }

    @Override
    public OrderInfo getOrderInfo(Integer orderId) {
        String sql = "SELECT * FROM order_info WHERE order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<OrderInfo> orderInfoList = namedParameterJdbcTemplate.query(sql, map, new OrderInfoRowMapper());

        if(orderInfoList.size() > 0) {
            return orderInfoList.get(0);
        } else {
            return null;
        }
    }
}
