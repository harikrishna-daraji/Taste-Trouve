package com.example.tastetrouverestaurantowner.Modal;

import java.util.List;

public class PendingOrderModal {
    public String _id,orderDate,total,orderStatus;
    public List<ProductOrderModal> products;

    public Address addressId;

    public OrderUser userId;

    public OrderUser getUserId() {
        return userId;
    }

    public void setUserId(OrderUser userId) {
        this.userId = userId;
    }



    public PendingOrderModal() {

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
        return products.size();
    }

    public Address getAddressId() {
        return addressId;
    }

    public void setAddressId(Address addressId) {
        this.addressId = addressId;
    }

    public String getStatus() {
        return orderStatus;
    }

    public void setStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }



    public List<ProductOrderModal> getProductOrderModalList() {
        return products;
    }

    public void setProductOrderModalList(List<ProductOrderModal> products) {
        this.products = products;
    }


}
