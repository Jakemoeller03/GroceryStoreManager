package com.cs3773.grocery.manager.sweproject.objects;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;

    @ManyToMany
    @JoinTable(
            name = "order_items",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items;

    private LocalDateTime orderTime;

    private int customerID; // You can replace with a @ManyToOne User later

    private long orderPrice;

    private boolean orderStatus; // true = executed, false = pending

    // Constructors
    public order() {}

    public order(List<Item> items, LocalDateTime orderTime, int customerID, long orderPrice, boolean orderStatus) {
        this.items = items;
        this.orderTime = orderTime;
        this.customerID = customerID;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
    }

    // Getters and setters
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public long getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(long orderPrice) {
        this.orderPrice = orderPrice;
    }

    public boolean isOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(boolean orderStatus) {
        this.orderStatus = orderStatus;
    }
}