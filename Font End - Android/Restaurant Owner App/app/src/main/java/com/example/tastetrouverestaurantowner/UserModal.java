package com.example.tastetrouverestaurantowner;

public class UserModal {



    String  _id;
    String  restaurantName;
    String  email;
    String  password;
    String  phoneNumber;



    public UserModal(String _id, String restaurantName, String email, String password, String phoneNumber) {
        this._id = _id;
        this.restaurantName = restaurantName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
