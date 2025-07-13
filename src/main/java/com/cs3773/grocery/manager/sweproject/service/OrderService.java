package com.cs3773.grocery.manager.sweproject.service;

import com.cs3773.grocery.manager.sweproject.objects.Item;
import com.cs3773.grocery.manager.sweproject.objects.order;
import com.cs3773.grocery.manager.sweproject.repository.ItemRepository;
import com.cs3773.grocery.manager.sweproject.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public order createOrder(order newOrder) {
        Set<Integer> itemIds = newOrder.getItems().stream()
                .filter(Objects::nonNull)
                .map(Item::getItemID)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<Item> attachedItems = itemRepository.findAllById(itemIds);


        newOrder.setItems(attachedItems);
        newOrder.setOrderTime(LocalDateTime.now());
        newOrder.setOrderStatus(false);

        // Optionally calculate order price
        long totalPrice = attachedItems.stream().mapToLong(Item::getItemPrice).sum();
        newOrder.setOrderPrice(totalPrice);

        return orderRepository.save(newOrder);
    }
    @Transactional
    public List<order> getAllOrders() {
        return orderRepository.findAll();
    }
    @Transactional
    public order getOrderById(int orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    public List<order> getOrdersSortedByTime(boolean ascending) {
        return orderRepository.findAll()
                .stream()
                .sorted((o1, o2) -> ascending ?
                        o1.getOrderTime().compareTo(o2.getOrderTime()) :
                        o2.getOrderTime().compareTo(o1.getOrderTime()))
                .collect(Collectors.toList());
    }

    public List<order> getOrdersSortedByCustomer() {
        return orderRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(order::getCustomerID))
                .collect(Collectors.toList());
    }

    public List<order> getOrdersSortedByPrice(boolean descending) {
        return orderRepository.findAll()
                .stream()
                .sorted((o1, o2) -> descending ?
                        Long.compare(o2.getOrderPrice(), o1.getOrderPrice()) :
                        Long.compare(o1.getOrderPrice(), o2.getOrderPrice()))
                .collect(Collectors.toList());
    }

    public order executeOrder(int orderId) {
        order order = getOrderById(orderId);

        if (order.isOrderStatus()) {
            throw new IllegalStateException("Order is already executed.");
        }

        // Reduce item quantities
        for (Item item : order.getItems()) {
            Optional<Item> optionalItem = itemRepository.findById(item.getItemID());
            if (optionalItem.isPresent()) {
                Item dbItem = optionalItem.get();
                if (dbItem.getItemQuantity() >= 1) {
                    dbItem.setItemQuantity(dbItem.getItemQuantity() - 1);
                    itemRepository.save(dbItem);
                } else {
                    throw new IllegalStateException("Item " + dbItem.getName() + " is out of stock.");
                }
            }
        }

        order.setOrderStatus(true);
        return orderRepository.save(order);
    }
}

