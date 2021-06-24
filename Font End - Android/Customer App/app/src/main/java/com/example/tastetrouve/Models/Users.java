package com.example.tastetrouve.Models;

public class Users {
    public String name,email,phone,dateofbirth, password, fcmToken;

    public Users(){

    }
    public Users(String name,String email,String phone,String dateofbirth, String password){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dateofbirth = dateofbirth;
        this.password = password;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getFcmToken() {
        return fcmToken;
    }
}
