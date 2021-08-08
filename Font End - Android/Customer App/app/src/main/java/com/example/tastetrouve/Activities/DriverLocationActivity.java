package com.example.tastetrouve.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.tastetrouve.Models.DriverLocationModel;
import com.example.tastetrouve.Models.UserTestModel;
import com.example.tastetrouve.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

public class DriverLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private MapboxMap mapboxMap;
    Point destination, origin;
    DriverLocationModel driverLocationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_driver_location);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        getDriverLocationFromFirebase();
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);

            }
        });
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            // Activate the MapboxMap LocationComponent to show user location
            // Adding in LocationComponentOptions is also an optional parameter
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(this, loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
            destination = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),locationComponent.getLastKnownLocation().getLatitude());
            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
        }
    }

    private void getDriverLocationFromFirebase() {
        FirebaseDatabase.getInstance().getReference("Drivers").child("60fb0074fd8abd572e9ebe86").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               driverLocationModel = snapshot.getValue(DriverLocationModel.class);
                Log.i("TAG","TAG: Driver location arrived "+driverLocationModel.getLatitude()+" "+driverLocationModel.getLongitude());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("TAG","TAG: Firebase child listener canceled: "+error.getMessage());
            }
        });
    }
}