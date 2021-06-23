package com.example.tastetrouve.Models;

public class UserModel {
    String _id;
    String email;
    String password;
    String displayname;
    String fcmToken;
    String phoneNumber;
    String dateOfBirth;

    public String get_id() {
        return _id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayname() {
        return displayname;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
}
