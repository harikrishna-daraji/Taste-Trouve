package com.example.tastetrouvedriver.Helper.Fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tastetrouvedriver.Helper.APIClient;
import com.example.tastetrouvedriver.Helper.ApiInterface;
import com.example.tastetrouvedriver.Helper.Model.DriverCurrentRequestModel;
import com.example.tastetrouvedriver.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;

import org.json.JSONObject;
import org.reactivestreams.Subscription;

import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView addressTV;
    private static final float DEFAULT_ZOOM = 14f;
    private View root;
    private LinearLayout acceptRejectLinear;
    private RelativeLayout rejectRelative, acceptRelative;
    private CardView finishCardView, newOrderCardView;
    FusedLocationProviderClient fusedLocationProviderClient;
    SharedPreferences sharedPreferences;
    boolean locationPermissionGranted = false;
    public static Location lastKnownLocation;
    final static int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1000;
    DriverCurrentRequestModel driverCurrentRequestModel;
    LocationRequest locationRequest;
    Marker userLocationMarker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.activity_map, container, false);
        initUI();
        prepareMap();
        getDriverCurrentOrder();
        return root;
    }

    private String getUserToken() {
        sharedPreferences = getActivity().getSharedPreferences("MySharedPref", getContext().MODE_PRIVATE);
        String token = sharedPreferences.getString("ownerId", "");
        return token;
    }

    private void prepareMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
            getLocationPermission();
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
            locationRequest = LocationRequest.create();
            locationRequest.setInterval(500);
            locationRequest.setFastestInterval(500);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        }

    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Log.i("TAG","TAG: Updated location"+locationResult.getLastLocation());
            if(mMap != null) {
                setUserLocationMarker(locationResult.getLastLocation());
            }
        }
    };

    private void startLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void setUserLocationMarker(Location location) {
        if(userLocationMarker == null) {
            MarkerOptions markerOptions  = new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude()));
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.yellow_car));
            markerOptions.rotation(location.getBearing());
            markerOptions.anchor((float) 0.5,(float) 0.5);
            userLocationMarker = mMap.addMarker(markerOptions);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),17));
        } else {
            userLocationMarker.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
            userLocationMarker.setRotation(location.getBearing());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),17));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        startLocationUpdate();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopLocationUpdate();
    }

    private void stopLocationUpdate() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void initUI() {
        acceptRejectLinear = root.findViewById(R.id.acceptRejectLinear);
        rejectRelative = root.findViewById(R.id.rejectRelative);
        acceptRelative = root.findViewById(R.id.acceptRelative);
        newOrderCardView = root.findViewById(R.id.newOrderCardView);
        finishCardView = root.findViewById(R.id.finishCardView);
        addressTV = root.findViewById(R.id.addressTV);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        updateLocationUI();
        getDeviceLocation();
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                Log.i("TAG", "TAG: User current location received");
                                LatLng currentLtdLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLtdLng, DEFAULT_ZOOM));
                            }
                        } else {
                            Log.i("TAG", "Current location is null. Using defaults.");
                            Log.i("TAG", "Exception: %s", task.getException());
                            LatLng montreal = new LatLng(45.5016889, -73.567256);
                            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(montreal)
                                    .zoom(DEFAULT_ZOOM)
                                    .bearing(0)
                                    .tilt(25)
                                    .build()));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
//                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
//                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDriverCurrentOrder() {
        String token = getUserToken();
        if(!token.isEmpty()) {
            try {
                Log.i("TAG","TAG: Latest token: "+token);
                APIClient.getInstance().getApi().getCurrentOrder(token).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            JSONObject finalResponse = new JSONObject(response.body().string());
                            driverCurrentRequestModel = new DriverCurrentRequestModel(finalResponse);
                            if(driverCurrentRequestModel.getOrderId() != null) {
                                newOrderCardView.setVisibility(View.VISIBLE);
                                addressTV.setText(driverCurrentRequestModel.getOrderId().getAddressId().getAddress());
                            }
                        } catch (Exception ex) {
                            Log.i("TAG","TAG: "+ex.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("TAG","TAG: Server failure"+t.getMessage());
                    }
                });

//                io.reactivex.Observable<ResponseBody> observable= APIClient.getCurrentOrderInstance().getApi().getCurrentOrder(token);
//                observable.subscribe(new Consumer<ResponseBody>() {
//                    @Override
//                    public void accept(ResponseBody responseBody) throws Exception {
//                        try {
//                            Log.i("TAG","TAG: the response is here");
//                        } catch (Exception ex) {
//                            Log.i("TAG","TAG: Inner catch"+ex.getMessage());
//                        }
//                    }
//                },throwable -> Log.i("TAG","TAG: Throwable: "+throwable.getMessage()));
            } catch (Exception ex) {
                Log.i("TAG","TAG: Outer catch"+ex.getMessage());
            }

        }
    }

    private void updateStatusOfCurrentOrder() {

    }
}