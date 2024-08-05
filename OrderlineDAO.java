package com.Ordermanagment1;

import java.time.LocalDateTime;
	
public class OrderlineDAO {
    private String orderLineId;
    private char isActive;
    private LocalDateTime created;
    private String createdBy;
    private LocalDateTime updated;
    private String updatedBy;
    private char isSale;
    private String product;
    private String umo;
    private double quantity;
    private double price;
    private String orderId;


  
    public OrderlineDAO(String orderLineId, char isActive, LocalDateTime created, String createdBy, LocalDateTime updated, String updatedBy,char isSale, String product, String umo, double quantity, double price,String orderId) {
        this.orderLineId = orderLineId;
        this.isActive = isActive;
        this.created = created;
        this.createdBy = createdBy;
        this.updated = updated;
        this.updatedBy = updatedBy;
        this.isSale = isSale;
        this.product = product;
        this.umo = umo;
        this.quantity = quantity;
        this.price = price;
        this.orderId=orderId;
    }


	// Getters and Setters
    public String getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(String orderLineId) {
        this.orderLineId = orderLineId;
    }

    public char getIsActive() {
        return isActive;
    }

    public void setIsActive(char isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public char getIsSale() {
        return isSale;
    }

    public void setIsSale(char isSale) {
        this.isSale = isSale;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getUmo() {
        return umo;
    }

    public void setUmo(String umo) {
        this.umo = umo;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
