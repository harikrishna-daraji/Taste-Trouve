package com.example.tastetrouve.Models;

import android.util.Log;

import java.io.Serializable;

public class ItemProductModel implements Serializable {
    String _id, restaurantId, categoryId, subCategoryId, name, image, description, calories, DeliveryTime,favourite,specialType;
    int price, quantity,cutOffPrice;
    boolean kidSection;
    boolean popular;

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String get_id() {
        return _id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getCalories() {
        return calories;
    }

    public String getDeliveryTime() {
        return DeliveryTime;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isKidSection() {
        return kidSection;
    }

    public boolean isPopular() {
        return popular;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public String getSpecialType() {
        return specialType;
    }

    public int getCutOffPrice() {
     return price -((price*20)/100);


    }

}
