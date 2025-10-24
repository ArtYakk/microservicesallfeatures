package com.order.controller;

import com.order.entity.Order;
import com.order.model.CustomerResponse;
import com.order.service.CustomerService;
import com.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    private CustomerService customerService;
    private OrderService orderService;

    public OrderController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @PostMapping("/orders/placeorder")
    public ResponseEntity<String> postMethodName(@RequestBody Order newOrder) {
        //fetch customer from customer service for the given cust id
        CustomerResponse customerResponse=customerService.getCustomer(newOrder.getCustomerId());
        if((customerResponse.getErrorMessage()!=null) && customerResponse.getErrorMessage().contains("Customer Not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customerResponse.getErrorMessage());

        }else if((customerResponse.getErrorMessage()!=null) && customerResponse.getErrorMessage().contains("Customer Service Temporarily Unavailable"))
        {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Customer Service Temporarily Unavailable. Please try later.");
        }

        return ResponseEntity.ok("Order Placed for customer: "+orderService.createOrder(newOrder));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

}
