package com.cs3773.grocery.manager.sweproject.objects;

import jakarta.persistence.*;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemID;

    private String name;
    private String imageID;

    @Column(length = 1000)
    private String description;

    private Long itemPrice;
    private Integer itemQuantity;

    private String discountCode;
    private Boolean isOnSale;

    // Constructors
    public Item() {}

    public Item(String name, String imageID, String description, Long itemPrice,
                Integer itemQuantity, String discountCode, Boolean isOnSale) {
        this.name = name;
        this.imageID = imageID;
        this.description = description;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.discountCode = discountCode;
        this.isOnSale = isOnSale;
    }

    // Getters and Setters
    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Long itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public Boolean getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Boolean onSale) {
        isOnSale = onSale;
    }
}
