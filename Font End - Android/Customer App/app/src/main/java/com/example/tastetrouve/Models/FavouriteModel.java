package com.example.tastetrouve.Models;

import java.util.List;

public class FavouriteModel {
    public String _id,userId;
   public  ItemProductModel productId;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ItemProductModel getProductId() {
        return productId;
    }

    public void setProductId(ItemProductModel productId) {
        this.productId = productId;
    }
}
