package com.example.tastetrouvedriver.Helper.Model;

import java.util.List;

public class MyOrderModel {
    public String _id,orderDate,total,orderStatus,orderImage,ratingReview;
    public Double ratingStar;
    public List<ProductOrderModel> products;

    public String getOrderImage() {
        return products.get(0).getImage();
    }

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

    public List<ProductOrderModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductOrderModel> products) {
        this.products = products;
    }

    public MyOrderModel(String _id, String orderDate, String total, String orderStatus, List<ProductOrderModel> products, String ratingReview, Double ratingStar) {
        this._id = _id;
        this.orderDate = orderDate;
        this.total = total;
        this.orderStatus = orderStatus;
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

    public Double getRating() {
        return ratingStar;
    }

    public void setRating(Double ratingStar) {
        this.ratingStar = ratingStar;
    }


}
