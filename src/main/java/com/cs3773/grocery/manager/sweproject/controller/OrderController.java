package com.cs3773.grocery.manager.sweproject.controller;

import com.cs3773.grocery.manager.sweproject.objects.order;
import com.cs3773.grocery.manager.sweproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    public List<order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public order getOrderById(@PathVariable int orderId) {
        return orderService.getOrderById(orderId);
    }


    @GetMapping("/sort/{sortBy}")
    public List<order> getSortedOrders(@PathVariable String sortBy,
                                       @RequestParam(defaultValue = "true") boolean ascending) {
        return switch (sortBy.toLowerCase()) {
            case "time" -> orderService.getOrdersSortedByTime(ascending);
            case "customer" -> orderService.getOrdersSortedByCustomer();
            case "price" -> orderService.getOrdersSortedByPrice(!ascending); // Note: price uses descending logic
            default -> throw new IllegalArgumentException("Invalid sort parameter: " + sortBy +
                    ". Supported values: time, customer, price");
        };
    }

    @PostMapping
    public ResponseEntity<order> createOrder(@RequestBody order newOrder) {
        order createdOrder = orderService.createOrder(newOrder);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PostMapping("/{orderId}/execute")
    public order executeOrder(@PathVariable int orderId) {
        return orderService.executeOrder(orderId);
    }
}

