package com.ispan.pcbuy.controller;

import com.ispan.pcbuy.dto.CreateCartRequest;
import com.ispan.pcbuy.dto.CreateOrderRequest;
import com.ispan.pcbuy.dto.OrderInfoRequest;
import com.ispan.pcbuy.dto.OrderStateRequest;
import com.ispan.pcbuy.model.Cart;
import com.ispan.pcbuy.model.Order;
import com.ispan.pcbuy.model.OrderInfo;
import com.ispan.pcbuy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid  CreateOrderRequest createOrderRequest){

        Integer orderId  = orderService.createOrder(userId, createOrderRequest);

        Order order =orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PostMapping("/users/{orderId}/orderinfo")
    public ResponseEntity<HttpStatus> createOrderInfo(@PathVariable Integer orderId,
                                             @RequestBody @Valid OrderInfoRequest orderInfoRequest){
        orderService.createOrderInfo(orderId, orderInfoRequest);

        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/users/{orderId}/orderinfo")
    public ResponseEntity<OrderInfo> getOrderInfo(@PathVariable Integer orderId){

        OrderInfo orderInfo = orderService.getOrderInfo(orderId);

        return ResponseEntity.status(HttpStatus.OK).body(orderInfo);
    }

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<?> getOrders(@PathVariable Integer userId){

        List<Order> orderList =orderService.getOrderByUserId(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderList);
    }

    @GetMapping("/admin/{userId}/orders")
    public ResponseEntity<?> getOrderAll(@PathVariable Integer userId){

        List<Order> orderList = orderService.getOrderAll(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderList);
    }

    @PutMapping("/users/{userId}/orders")
    public void updateOrders(@PathVariable Integer userId, @RequestBody OrderStateRequest orderStateRequest){
        orderService.updateOrders(userId, orderStateRequest);
    }

    @PostMapping("/users/{userId}/cart")
    public ResponseEntity<?> createCart(@PathVariable Integer userId,
                                        @RequestBody @Valid CreateCartRequest createCartRequest){
        System.out.println();
        orderService.createCart(userId, createCartRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @GetMapping("/users/{userId}/cart")
    public ResponseEntity<List<Cart>> getCart(@PathVariable Integer userId){

        List<Cart> cartList = orderService.getCart(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartList);
    }

    @DeleteMapping("/users/{userId}/cart")
    public void clearCart(@PathVariable Integer userId){

        orderService.clearCart(userId);

    }
}
