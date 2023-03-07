package com.ispan.pcbuy.service.impl;

import com.ispan.pcbuy.dao.OrderDao;
import com.ispan.pcbuy.dao.ProductDao;
import com.ispan.pcbuy.dao.UserDao;
import com.ispan.pcbuy.dto.*;
import com.ispan.pcbuy.model.*;
import com.ispan.pcbuy.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        //檢查 user 是否存在

        User user = userDao.getUserById(userId);

        if (user == null){
            log.warn("該 userId {} 不存在",userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            // 檢查 product 是否存在、庫存是否足夠
            if(product == null){
                log.warn("商品 {} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()) {
                log.warn("商品 {} 庫存數量不足，無法購買！剩餘庫存{}，欲購買數量{}", product.getProductName(),product.getStock(),buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            //扣除商品庫存
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

            // 計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;

            //轉換BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        //創建訂單
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);
        orderDao.clearCart(userId);
        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {

        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItemList);

        return order;
    }

    @Override
    public void createCart(Integer userId, CreateCartRequest createCartRequest) {

        List<Cart> cartList = new ArrayList<>();
        for(BuyItem buyItem : createCartRequest.getBuyItemList()) {

            //轉換BuyItem to Cart
            Cart cart = new Cart();
            cart.setProductId(buyItem.getProductId());
            cartList.add(cart);
        }
        orderDao.createCart(userId, cartList);
    }

    @Override
    public List<Cart> getCart(Integer userId) {
        return orderDao.getCart(userId);
    }

    @Override
    public void clearCart(Integer userId) {
        orderDao.clearCart(userId);
    }

    @Override
    public List<Order> getOrderByUserId(Integer userId) {
        List<Order> orderList;

        User user = userDao.getUserById(userId);
        if(user != null){
            orderList = orderDao.getOrderByUserId(userId);
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        for (int i=0 ; i<orderList.size() ; i++){

            List<OrderItem> orderItemList =orderDao.getOrderItemsByOrderId(orderList.get(i).getOrderId());

            orderList.get(i).setOrderItemList(orderItemList);
        }

        return orderList;
    }

    @Override
    public List<Order> getOrderAll(Integer userId) {
        List<Order> orderList;

        User user = userDao.getUserById(userId);
        if(user.getAuth().contains("admin")){
            orderList = orderDao.getOrderAll();
        }else {
            log.warn(" {} 不是管理員", user.getUsername());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        for (int i=0 ; i<orderList.size() ; i++){

            List<OrderItem> orderItemList =orderDao.getOrderItemsByOrderId(orderList.get(i).getOrderId());

            orderList.get(i).setOrderItemList(orderItemList);
        }

        return orderList;
    }

    @Override
    public void updateOrders(Integer userId, OrderStateRequest orderStateRequest) {
        Integer orderId = orderStateRequest.getOrderId();
        String state = orderStateRequest.getState();
        User user = userDao.getUserById(userId);
        Boolean isContain = false;

        List<Order> orderList = orderDao.getOrderByUserId(userId);
        for(Order order:orderList){
            if (orderId.equals(order.getOrderId())){
                isContain = true;
            }
        }
        if (isContain || user.getAuth().contains("admin")){
            orderDao.updateOrders(orderId, state);
        }else {
            log.warn("該筆訂單不存在於 {} 使用者下",userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void createOrderInfo(Integer orderId, OrderInfoRequest orderInfoRequest) {
        orderDao.createOrderInfo(orderId, orderInfoRequest);
    }

    @Override
    public OrderInfo getOrderInfo(Integer orderId) {

        OrderInfo orderInfo = orderDao.getOrderInfo(orderId);

        return orderInfo;
    }
}
