package com.example.tastetrouvedriver.Helper.Model;

import android.util.Log;

import org.json.JSONObject;

import java.util.List;

public class DriverCurrentRequestModel {
    String _id;
    String deliveryUser;

    public String get_id() {
        return _id;
    }

    public String getDeliveryUser() {
        return deliveryUser;
    }

    public OrderModel getOrderId() {
        return orderId;
    }

    public RestaurantModel getRestroId() {
        return restroId;
    }

    public String getStatus() {
        return status;
    }

    OrderModel orderId;
    RestaurantModel restroId;
    String status;


    public DriverCurrentRequestModel(JSONObject jsonObject) {
        try {
            _id = jsonObject.getString("_id");
            deliveryUser = jsonObject.getString("deliveryUser");
            orderId = new OrderModel(jsonObject.getJSONObject("orderId"));
            restroId = new RestaurantModel(jsonObject.getJSONObject("restroId"));
            status = jsonObject.getString("status");
        } catch (Exception ex) {
            Log.i("TAG","TAG: DriverCurrentRequestModel Exception "+ex.getMessage());
        }
    }

}
