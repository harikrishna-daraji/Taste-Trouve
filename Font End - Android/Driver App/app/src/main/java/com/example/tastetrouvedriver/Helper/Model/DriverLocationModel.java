package com.example.tastetrouvedriver.Helper.Model;

public class DriverLocationModel {
    double latitude;
    double longitude;

    public DriverLocationModel() {}

    public DriverLocationModel(double latitude, double longitude ) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
