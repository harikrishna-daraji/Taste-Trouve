package com.example.tastetrouve.Models;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class AddressModel implements Serializable {

    private String _id;
    private String userId;
    private String address;
    private String lat;
    private String _long;

    public AddressModel(JSONObject jsonObject) {
        try {
            _id = jsonObject.getString("_id");
            userId = jsonObject.getString("userId");
            address = jsonObject.getString("address");
            lat = jsonObject.getString("lat");
            _long = jsonObject.getString("_long");
        } catch (Exception ex) {
            Log.i("TAG","TAG: AddressModel Exception: "+ex.getMessage());
        }
    }

    public String get_id() {
        return _id;
    }

    public String getUserId() {
        return userId;
    }

    public String getAddress() {
        return address;
    }

    public String getLat() {
        return lat;
    }

    public String get_long() {
        return _long;
    }
}
