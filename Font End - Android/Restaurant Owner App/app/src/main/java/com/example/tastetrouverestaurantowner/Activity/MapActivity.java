package com.example.tastetrouverestaurantowner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tastetrouverestaurantowner.GlobalObjects;
import com.example.tastetrouverestaurantowner.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String address, city, province, country, postalCode, latitude, longitude;
    SearchView searchView;
    RelativeLayout locationDetailsBottomRelative;
    TextView addressTV, cityTV, provinceTV, countryTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        prepareMap();
        initUI();
    }

    private void prepareMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void initUI() {
        locationDetailsBottomRelative = findViewById(R.id.locationDetailsBottomRelative);
        findViewById(R.id.hideBottomViewTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationDetailsBottomRelative.setVisibility(View.GONE);
            }
        });
        addressTV = findViewById(R.id.addressTV);
        cityTV = findViewById(R.id.cityTV);
        provinceTV = findViewById(R.id.provinceTV);
        countryTV = findViewById(R.id.countryTV);

        findViewById(R.id.saveTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!latitude.isEmpty() && !longitude.isEmpty() && !address.isEmpty()) {
                    Intent intent = new Intent();
                    intent.putExtra("latitude",latitude);
                    intent.putExtra("longitude",longitude);
                    intent.putExtra("address",address);
                    setResult(GlobalObjects.MAP_REQUEST_CODE,intent);
                    finish();
                }
            }
        });

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Geocoder geocoder = new Geocoder(MapActivity.this);
                List<Address> list = null;
                if(!query.isEmpty()) {
                    try {
                        list = geocoder.getFromLocationName(query,1);
                        latitude = String.valueOf(list.get(0).getLatitude());
                        longitude = String.valueOf(list.get(0).getLongitude());
                        LatLng latLng = new LatLng(list.get(0).getLatitude(),list.get(0).getLongitude());
                        String address1 = list.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String address2 = list.get(0).getAddressLine(1); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        city = list.get(0).getLocality();
                        province = list.get(0).getAdminArea();
                        country = list.get(0).getCountryName();
                        postalCode = list.get(0).getPostalCode();
                        address = address1;
                        Log.i("TAG","TAG: City: "+city);
                        Log.i("TAG","TAG: province: "+province);
                        Log.i("TAG","TAG: country: "+country);
                        Log.i("TAG","TAG: postalCode: "+postalCode);
                        Log.i("TAG","TAG: address: "+address);
                        addressTV.setText(address);
                        cityTV.setText(city);
                        provinceTV.setText(province);
                        countryTV.setText(country);
                        locationDetailsBottomRelative.setVisibility(View.VISIBLE);

                        mMap.addMarker(new MarkerOptions().position(latLng));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
                    } catch (Exception ex) {
                        Log.i("TAG","TAG "+ex.getMessage());
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}