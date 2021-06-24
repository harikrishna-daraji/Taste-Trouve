package com.example.tastetrouveadmin;

public class UData {
    String _id;
    String restaurantName,email,phoneNumber,address;

    public UData(String restaurantName, String email, String phoneNumber, String address , String _id) {
        this.restaurantName = restaurantName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this._id = _id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
