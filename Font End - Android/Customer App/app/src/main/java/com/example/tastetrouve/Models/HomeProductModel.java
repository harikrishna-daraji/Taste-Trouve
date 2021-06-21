package com.example.tastetrouve.Models;

import java.util.List;

public class HomeProductModel {
    List<CategoryModel> categoryObject;
    List<KidSectionModel> kidsSection;
    List<PopularSectionModel> popular;

    public List<CategoryModel> getCategoryObject() {
        return categoryObject;
    }

    public List<KidSectionModel> getKidsSection() {
        return kidsSection;
    }

    public List<PopularSectionModel> getPopular() {
        return popular;
    }
}
