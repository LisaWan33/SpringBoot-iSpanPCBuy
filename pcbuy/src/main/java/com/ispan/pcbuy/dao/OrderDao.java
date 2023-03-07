package com.ispan.pcbuy.dao;

import com.ispan.pcbuy.dto.OrderInfoRequest;
import com.ispan.pcbuy.model.Cart;
import com.ispan.pcbuy.model.Order;
import com.ispan.pcbuy.model.OrderInfo;
import com.ispan.pcbuy.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    void createCart(Integer userId, List<Cart> cartList);

    List<Cart> getCart(Integer userId);

    void clearCart(Integer userId);

    List<Order> getOrderByUserId(Integer userId);

    List<Order> getOrderAll();

    void updateOrders(Integer orderId, String state);

    void createOrderInfo(Integer orderId, OrderInfoRequest orderInfoRequest);

    OrderInfo getOrderInfo (Integer orderId);
}
