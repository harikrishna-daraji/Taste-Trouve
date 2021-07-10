package com.example.tastetrouve.Models;

import android.util.Log;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class CartProductModel {
    String _id;
    String restaurantId;
    String categoryId;
    String subCategoryId;
    String name;
    String image;
    int price;
    String description;
    String calories;

    int quantity;
    boolean kidSection;
    boolean popular;
    boolean visibleStatus;
    String DeliveryTime;

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

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getCalories() {
        return calories;
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

    public boolean isVisibleStatus() {
        return visibleStatus;
    }

    public String getDeliveryTime() {
        return DeliveryTime;
    }


    public JSONObject prepareOrderModel() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",_id);
            jsonObject.put("name",name);
            jsonObject.put("quantity",String.valueOf(quantity));
            jsonObject.put("image",image);
            jsonObject.put("price",String.valueOf(price));
        } catch (Exception ex) {
            Log.i("TAG","TAG: CartProductModel Order json making error: "+ex.getMessage());
        }
        return  jsonObject;
    }
}
