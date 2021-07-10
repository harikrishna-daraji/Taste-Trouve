package com.example.tastetrouverestaurantowner.Modal;

public class DriverModal {


    String  _id;
    String  displayname;
    String  email;
    String  password;
    String  phoneNumber;

    public DriverModal(String _id, String displayname, String email, String password, String phoneNumber) {
        this._id = _id;
        this.displayname = displayname;
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

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
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
