package com.example.tastetrouvedriver.Helper.Model;

import android.util.Log;

import org.json.JSONObject;

public class ProductModel {
    String _id;
    String image;
    String quantity;
    String price;

    public String get_id() {
        return _id;
    }

    public String getImage() {
        return image;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    String name;
    String id;

    public ProductModel(JSONObject jsonObject) {
        try {
            _id = jsonObject.getString("_id");
            image = jsonObject.getString("image");
            quantity = jsonObject.getString("quantity");
            price = jsonObject.getString("price");
            name = jsonObject.getString("name");
            id = jsonObject.getString("id");
        } catch (Exception ex) {
            Log.i("TAG","TAG: ProductModel Exception: "+ex.getMessage());
        }
    }

}
