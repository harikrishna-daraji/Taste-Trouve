package com.example.tastetrouverestaurantowner.Modal;

import java.util.List;

public class PendingOrderModal {
    String orderId,orderDate,totalPrice,itemCount,address,status,userName,userPhone;
    List<ProductOrderModal> productOrderModalList;

    public PendingOrderModal(String orderId, String orderDate, String totalPrice, String itemCount, String address, String status, String userName, String userPhone, List<ProductOrderModal> productOrderModalList) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.itemCount = itemCount;
        this.address = address;
        this.status = status;
        this.userName = userName;
        this.userPhone = userPhone;
        this.productOrderModalList = productOrderModalList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public List<ProductOrderModal> getProductOrderModalList() {
        return productOrderModalList;
    }

    public void setProductOrderModalList(List<ProductOrderModal> productOrderModalList) {
        this.productOrderModalList = productOrderModalList;
    }


}
