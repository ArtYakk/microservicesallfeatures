package com.order.service;

import com.order.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order createOrder(Order newOrder);
}
