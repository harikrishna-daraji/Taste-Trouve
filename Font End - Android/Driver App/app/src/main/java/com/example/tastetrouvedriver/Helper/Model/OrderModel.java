package com.example.tastetrouvedriver.Helper.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    String _id;
    String userId;
    AddressModel addressId;

    public String get_id() {
        return _id;
    }

    public String getUserId() {
        return userId;
    }

    public AddressModel getAddressId() {
        return addressId;
    }

    public int getDelivery() {
        return delivery;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public int getTax() {
        return tax;
    }

    public int getTotal() {
        return total;
    }

    public List<ProductModel> getProducts() {
        return product;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    int delivery;
    String orderStatus;
    int tax;
    int total;
    List<ProductModel> product = new ArrayList<>();
    String orderDate;
    String restaurantId;

    public OrderModel(JSONObject jsonObject) {
        try {
            _id = jsonObject.getString("_id");
            userId = jsonObject.getString("userId");
            addressId = new AddressModel(jsonObject.getJSONObject("addressId"));
            delivery = jsonObject.getInt("delivery");
            orderStatus = jsonObject.getString("orderStatus");
            tax = jsonObject.getInt("tax");
            total = jsonObject.getInt("total");

            JSONArray jsonArray = jsonObject.getJSONArray("products");
            for(int index=0;index<jsonArray.length();index++) {
                product.add(new ProductModel(jsonArray.getJSONObject(index)));
            }

            orderDate = jsonObject.getString("orderDate");
            restaurantId = jsonObject.getString("restaurantId");
        } catch (Exception ex) {
            Log.i("TAG","TAG: OrderModel Exception"+ex.getMessage());
        }
    }

}
