package com.example.tastetrouverestaurantowner.Modal;

import com.google.gson.annotations.SerializedName;

public class Address {

    public String _id;

    public String userId;

    public String address;

    public String lat;

    @SerializedName("long")
    public String lng;
}
