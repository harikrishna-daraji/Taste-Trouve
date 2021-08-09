package com.example.tastetrouvedriver.Helper.Model;

import android.util.Log;

import org.json.JSONObject;

public class RestaurantModel {
    String userType;
    String _id;
    String restaurantName;
    String email;
    String password;

    public String getUserType() {
        return userType;
    }

    public String get_id() {
        return _id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    String fcmToken;
    String address;
    String phoneNumber;
    String status;
    String lat;
    String lng;

    public RestaurantModel(JSONObject jsonObject) {
        try {
            userType = jsonObject.getString("userType");
            _id = jsonObject.getString("_id");
            restaurantName = jsonObject.getString("restaurantName");
            email = jsonObject.getString("email");
            password = jsonObject.getString("password");
            fcmToken = jsonObject.getString("fcmToken");
            address = jsonObject.getString("address");
            phoneNumber = jsonObject.getString("phoneNumber");
            status = jsonObject.getString("status");
            lat = jsonObject.getString("lat");
            lng = jsonObject.getString("lng");
        } catch (Exception ex) {
            Log.i("TAG","TAG: RestaurantModel Exception: "+ex.getMessage());
        }
    }

}
