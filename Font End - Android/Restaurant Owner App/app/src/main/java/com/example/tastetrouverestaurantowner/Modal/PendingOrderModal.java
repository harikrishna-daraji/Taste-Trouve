package com.example.tastetrouverestaurantowner.Modal;

import java.util.List;

public class PendingOrderModal {
    String _id,orderDate,total,address,orderStatus,userName,userPhone;
    int itemCount;
    List<ProductOrderModal> products;

    public PendingOrderModal(String _id, String orderDate, String total, int itemCount, String address, String orderStatus, String userName, String userPhone, List<ProductOrderModal> products) {
        this._id = _id;
        this.orderDate = orderDate;
        this.total = total;
        this.itemCount = itemCount;
        this.address = address;
        this.orderStatus = orderStatus;
        this.userName = userName;
        this.userPhone = userPhone;
        this.products = products;
    }

    public String getOrderId() {
        return _id;
    }

    public void setOrderId(String orderId) {
        this._id = _id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getTotalPrice() {
        return total;
    }

    public void setTotalPrice(String total) {
        this.total = total;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return orderStatus;
    }

    public void setStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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
        return products;
    }

    public void setProductOrderModalList(List<ProductOrderModal> products) {
        this.products = products;
    }


}
