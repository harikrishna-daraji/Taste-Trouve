package com.example.tastetrouvedriver.Helper.Model;

public class DriverLocationModel {
    double latitude;
    double longitude;
    String route;

    public DriverLocationModel() {}

    public DriverLocationModel(double latitude, double longitude, String route) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.route = route;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getRoute() {
        return route;
    }
}
