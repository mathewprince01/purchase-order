package com.Ordermanagment1;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
		private String orderId;
	    private char isActive;
	    private LocalDateTime created;
	    private String createdBy;
	    private LocalDateTime updated;
	    private String updatedBy;
	    private char isSale;
	    private LocalDate orderDate;
	    private String vendor;
	    private LocalDate edd;
	    private String shippingAddress;
	    private List<OrderlineDAO> listorderline=new ArrayList<>();

   public OrderDAO(String orderId, char isActive, LocalDateTime created, String createdBy, LocalDateTime updated, String updatedBy, char isSale, LocalDate orderDate, String vendor, LocalDate edd, String shippingAddress) {
        this.orderId = orderId;
        this.isActive = isActive;
        this.created = created;
        this.createdBy = createdBy;
        this.updated = updated;
        this.updatedBy = updatedBy;
        this.isSale = isSale;
        this.orderDate = orderDate;
        this.vendor = vendor;
        this.edd = edd;
        this.shippingAddress = shippingAddress;
    }
   public OrderDAO() {
	   
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public LocalDate getEdd() {
        return edd;
    }

    public void setEdd(LocalDate edd) {
        this.edd = edd;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
    public List<OrderlineDAO> getListorderline() {
		return listorderline;
	}
	public void setListorderline(List<OrderlineDAO> listorderline) {
		this.listorderline = listorderline;
	}

}
