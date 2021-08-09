package com.example.tastetrouvedriver.Helper.Model;

import android.util.Log;

import org.json.JSONObject;

public class AddressModel {
    String _id;
    String userId;

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

    String address;
    String lat;
    String _long;

    public AddressModel(JSONObject jsonObject) {
        try {
            _id = jsonObject.getString("_id");

            address = jsonObject.getString("address");
            lat = jsonObject.getString("lat");
            _long = jsonObject.getString("long");
        } catch (Exception ex) {
            Log.i("TAG","TAG: AddressModel exception "+ex.getMessage());
        }
    }


}
