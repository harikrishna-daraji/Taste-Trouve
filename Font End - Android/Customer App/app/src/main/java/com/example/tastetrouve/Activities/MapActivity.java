package com.example.tastetrouve.Activities;

import static com.google.android.libraries.places.api.Places.createClient;
import static com.google.android.libraries.places.api.Places.initialize;
import static com.google.android.libraries.places.api.Places.isInitialized;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends BaseActivity implements OnMapReadyCallback {

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
                callAddAddressApi();
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

    private String getUserToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("AuthenticationTypes",MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("signUpDone",false);
        if(isLoggedIn) {
            String token = sharedPreferences.getString("token","");
            return token;
        } else {
            return "";
        }
    }



    private void callAddAddressApi() {
        String token = getUserToken();
        if(!token.isEmpty() && !latitude.isEmpty() && !longitude.isEmpty() && !address.isEmpty()) {
            try{
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                apiInterface.addAddress(token,address,latitude,longitude).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.i("TAG","TAG: Code "+response.code()+" Message: "+response.message());
                            if(response.code() == 200) {
                                Intent intent = new Intent();
                                intent.putExtra("addressStored",true);
                                setResult(GlobalObjects.MAP_REQUEST_CODE,intent);
                                finish();
                            }
                        } catch (Exception ex) {
                            Log.i("TAG","TAG "+ex.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("TAG","TAG: Server failure: "+t.getMessage());
                    }
                });

            } catch (Exception ex) {
                Log.i("TAG","TAG "+ex.getMessage());
            }
        }

    }

}