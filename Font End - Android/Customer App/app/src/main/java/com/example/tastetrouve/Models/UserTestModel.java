package com.example.tastetrouve.Models;

public class UserTestModel {
    String name;
    String password;
    String email;
    String phone;
    String dateofbirth;
    String NewPassword;

    public UserTestModel(){

    }
    public UserTestModel(String name, String password, String email, String phone, String dateofbirth, String NewPassword) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.dateofbirth = dateofbirth;
        this.NewPassword = NewPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String newPassword) {
        NewPassword = newPassword;
    }
}
