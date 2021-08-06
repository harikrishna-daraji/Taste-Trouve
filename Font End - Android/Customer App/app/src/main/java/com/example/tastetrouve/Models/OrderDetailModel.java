package com.example.tastetrouve.Models;

import java.util.List;

public class OrderDetailModel {
    public String _id,orderDate,total,orderStatus,delivery,tax,ratingReview;
    public Float ratingStar;
    public List<ProductOrderModel> products;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public List<ProductOrderModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductOrderModel> products) {
        this.products = products;
    }

    public OrderDetailModel(String _id, String orderDate, String total, String orderStatus, String delivery, String tax, List<ProductOrderModel> products,String ratingReview, Float ratingStar) {
        this._id = _id;
        this.orderDate = orderDate;
        this.total = total;
        this.orderStatus = orderStatus;
        this.delivery = delivery;
        this.tax = tax;
        this.products = products;
        this.ratingReview = ratingReview;
        this.ratingStar = ratingStar;
    }

    public String getRatingReview() {
        return ratingReview;
    }

    public void setRatingReview(String ratingReview) {
        this.ratingReview = ratingReview;
    }

    public float getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(Float ratingStar) {
        this.ratingStar = ratingStar;
    }


}
