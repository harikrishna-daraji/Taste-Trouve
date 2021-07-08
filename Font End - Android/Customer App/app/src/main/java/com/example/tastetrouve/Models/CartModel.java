package com.example.tastetrouve.Models;

public class CartModel {

    String _id;
    String userId;
    CartProductModel productId;


    int quantity;

    public String get_id() {
        return _id;
    }

    public String getUserId() {
        return userId;
    }

    public CartProductModel getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
