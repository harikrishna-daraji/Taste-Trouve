package com.example.tastetrouve.Models;

public class Users {
    public String name,email,phone,dateofbirth, password, fcmToken ,NewPassword;

    public Users(){

    }
    public Users(String name,String email,String phone,String dateofbirth, String password, String NewPassword){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dateofbirth = dateofbirth;
        this.password = password;
        this.NewPassword = NewPassword;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String newPassword) {
        NewPassword = newPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getFcmToken() {
        return fcmToken;
    }
}
