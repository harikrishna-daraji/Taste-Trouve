package com.example.tastetrouvedriver.Helper.Model;

public class PastOrderModel {
    String _id;
    String deliveryUser;

    MyOrderModel orderId;
    String restroId;
    String status;


    public PastOrderModel(String _id, String deliveryUser, MyOrderModel orderId, String restroId, String status) {
        this._id = _id;
        this.deliveryUser = deliveryUser;
        this.orderId = orderId;
        this.restroId = restroId;
        this.status = status;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDeliveryUser() {
        return deliveryUser;
    }

    public void setDeliveryUser(String deliveryUser) {
        this.deliveryUser = deliveryUser;
    }

    public MyOrderModel getOrderId() {
        return orderId;
    }

    public void setOrderId(MyOrderModel orderId) {
        this.orderId = orderId;
    }

    public String getRestroId() {
        return restroId;
    }

    public void setRestroId(String restroId) {
        this.restroId = restroId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
