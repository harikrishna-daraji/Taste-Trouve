package com.example.tastetrouve.Models;

import java.util.List;

public class HomeProductModel {
    List<RestaurantModel> restaurants;
    List<CategoryModel> categoryObject;
    List<KidSectionModel> kidsSection;
    List<PopularSectionModel> popular;
    String cart;

    public List<CategoryModel> getCategoryObject() {
        return categoryObject;
    }

    public List<KidSectionModel> getKidsSection() {
        return kidsSection;
    }

    public List<PopularSectionModel> getPopular() {
        return popular;
    }

    public List<RestaurantModel> getRestaurants() {
        return restaurants;
    }

    public String getCart() {
        return cart;
    }
}
