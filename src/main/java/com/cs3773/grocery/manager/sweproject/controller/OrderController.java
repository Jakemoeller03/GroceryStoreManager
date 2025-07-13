package com.cs3773.grocery.manager.sweproject.controller;

import com.cs3773.grocery.manager.sweproject.objects.order;
import com.cs3773.grocery.manager.sweproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // a) Show all placed orders
    @GetMapping
    public List<order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // b) Show detailed information of a specific order
    @GetMapping("/{orderId}")
    public order getOrderById(@PathVariable int orderId) {
        return orderService.getOrderById(orderId);
    }

    // c) Sort orders by order time
    @GetMapping("/sort/time")
    public List<order> sortOrdersByTime(@RequestParam(defaultValue = "true") boolean ascending) {
        return orderService.getOrdersSortedByTime(ascending);
    }

    // d) Sort orders by customer ID
    @GetMapping("/sort/customer")
    public List<order> sortOrdersByCustomer() {
        return orderService.getOrdersSortedByCustomer();
    }

    // e) Sort orders by dollar amount
    @GetMapping("/sort/price")
    public List<order> sortOrdersByPrice(@RequestParam(defaultValue = "false") boolean descending) {
        return orderService.getOrdersSortedByPrice(descending);
    }

    // f) Execute an order: mark it as executed + reduce item quantity
    @PostMapping("/{orderId}/execute")
    public order executeOrder(@PathVariable int orderId) {
        return orderService.executeOrder(orderId);
    }
}

