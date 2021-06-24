package com.example.tastetrouve.Models;

public class RestaurantModel {
    String _id;
    String restaurantName;
    String email;
    String password;
    String fcmToken;
    String phoneNumber;
    boolean status;
    String userType;
    String image;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isStatus() {
        return status;
    }

    public String getUserType() {
        return userType;
    }

    public String getImage() {
        return image;
    }
}
