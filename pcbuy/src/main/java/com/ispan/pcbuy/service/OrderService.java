package com.ispan.pcbuy.service;

import com.ispan.pcbuy.dto.CreateCartRequest;
import com.ispan.pcbuy.dto.CreateOrderRequest;
import com.ispan.pcbuy.dto.OrderInfoRequest;
import com.ispan.pcbuy.dto.OrderStateRequest;
import com.ispan.pcbuy.model.Cart;
import com.ispan.pcbuy.model.Order;
import com.ispan.pcbuy.model.OrderInfo;

import java.util.List;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);

    void createCart(Integer userId, CreateCartRequest createCartRequest);

    List<Cart> getCart(Integer userId);

    void clearCart(Integer userId);

    List<Order> getOrderByUserId(Integer userId);

    List<Order> getOrderAll (Integer userId);

    void updateOrders(Integer userId, OrderStateRequest orderStateRequest);

    void createOrderInfo(Integer orderId, OrderInfoRequest orderInfoRequest);

    OrderInfo getOrderInfo(Integer orderId);
}
