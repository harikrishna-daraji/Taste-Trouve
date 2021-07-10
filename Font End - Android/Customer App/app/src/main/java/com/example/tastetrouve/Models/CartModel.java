package com.example.tastetrouve.Models;

public class CartModel {

    String _id;
    String userId;
    CartProductModel productId;
    String quantity;

    public String get_id() {
        return _id;
    }

    public String getUserId() {
        return userId;
    }

    public CartProductModel getProductId() {
        return productId;
    }

    public String getQuantity() {
        return quantity;
    }
}
