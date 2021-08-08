package com.example.tastetrouvedriver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.tastetrouvedriver.Helper.APIClient;
import com.example.tastetrouvedriver.Helper.ApiInterface;
import com.example.tastetrouvedriver.Helper.GlobalObjects;
import com.example.tastetrouvedriver.Helper.Model.AddressModel;
import com.example.tastetrouvedriver.Helper.Model.DriverCurrentRequestModel;
import com.example.tastetrouvedriver.Helper.Model.DriverLocationModel;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import java.lang.ref.WeakReference;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tastetrouvedriver.Helper.DirectionHelper.TaskLoadedCallback;
import com.example.tastetrouvedriver.Helper.Fragments.MapFragment;
import com.example.tastetrouvedriver.Helper.Fragments.PastOrderFragment;
import com.example.tastetrouvedriver.Helper.Fragments.SettingsFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
// classes needed to initialize map
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;
// classes to calculate a route
//import com.mapbox.services.android.navigation.ui.v5.MapOfflineOptions;
//import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
//import com.mapbox.services.android.navigation.ui.v5.NavigationView;
//import com.mapbox.services.android.navigation.ui.v5.NavigationViewOptions;
//import com.mapbox.services.android.navigation.ui.v5.listeners.BannerInstructionsListener;
//import com.mapbox.services.android.navigation.ui.v5.listeners.FeedbackListener;
//import com.mapbox.services.android.navigation.ui.v5.listeners.InstructionListListener;
//import com.mapbox.services.android.navigation.ui.v5.listeners.NavigationListener;
//import com.mapbox.services.android.navigation.ui.v5.listeners.RouteListener;
//import com.mapbox.services.android.navigation.ui.v5.listeners.SpeechAnnouncementListener;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.ui.v5.voice.SpeechPlayer;
import com.mapbox.services.android.navigation.v5.milestone.Milestone;
import com.mapbox.services.android.navigation.v5.milestone.MilestoneEventListener;
import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigation;
import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigationOptions;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


import android.util.Log;
// classes needed to launch navigation UI
import android.view.View;
import android.widget.Button;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.v5.routeprogress.ProgressChangeListener;

import org.json.JSONObject;

import java.util.List;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, View.OnClickListener, MapboxMap.OnMapClickListener, MapboxMap.OnMarkerClickListener {

    TabLayout tabs;
    MapFragment mapFragment;
    SettingsFragment settingsFragment;
    PastOrderFragment pastOrderFragment;
    RelativeLayout containerHomeRelative;
    DriverCurrentRequestModel driverCurrentRequestModel;
    private MapView mapView;
    private MapboxMap mapboxMap;
    private MapboxDirections client;
    private DirectionsRoute currentRoute;
    private static DirectionsRoute staticCurrentRoute;
    private PermissionsManager permissionsManager;
    SharedPreferences sharedPreferences;
    private LocationComponent locationComponent;
    private static final String ROUTE_LAYER_ID = "route-layer-id";
    private static final String ROUTE_SOURCE_ID = "route-source-id";
    private LocationEngine locationEngine;
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
    // Variables needed to listen to location updates
    private MainActivityLocationCallback callback = new MainActivityLocationCallback(this);
    TextView addressTV;
    CardView newOrderCardView, finishCardView;
    LinearLayout acceptRejectLinear;
    RelativeLayout acceptRelative, rejectRelative, goOnlineRelative;
    Point origin = Point.fromLngLat(45.501562071441825, -73.6300247057701);
    Point destination;
    TextView statusTV;
    int count=0;


    // variables for calculating and drawing a route
    private static final String TAG = "DirectionsActivity";
//    private NavigationMapRoute navigationMapRoute;
    // variables needed to initialize navigation
    final static int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1000;
    private static String tokenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        pastOrderFragment = new PastOrderFragment();
        settingsFragment = new SettingsFragment();
        initUI();
        tokenId = getUserToken();
    }

    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Prevent leaks
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates(callback);
        }
        mapView.onDestroy();

        mapView.onDestroy();
    }


    private void initUI() {
        goOnlineRelative = findViewById(R.id.goOnlineRelative);
        goOnlineRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statusTV.getText().equals(getString(R.string.offline))) {
                    changeDriverOnlineStatus(true);
                } else {
                    changeDriverOnlineStatus(false);
                }
            }
        });

        statusTV = findViewById(R.id.statusTV);

        rejectRelative = findViewById(R.id.rejectRelative);
        rejectRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatusOfCurrentOrder(GlobalObjects.rejected_by_driver);
            }
        });

        acceptRelative = findViewById(R.id.acceptRelative);
        acceptRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatusOfCurrentOrder(GlobalObjects.accepted_by_driver);
            }
        });

        acceptRejectLinear = findViewById(R.id.acceptRejectLinear);

        finishCardView = findViewById(R.id.finishCardView);
        finishCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishCurrentOrder();
            }
        });

        newOrderCardView = findViewById(R.id.newOrderCardView);
        addressTV = findViewById(R.id.addressTV);
        tabs = findViewById(R.id.tabs);
        tabs.getTabAt(1).select();
        containerHomeRelative = findViewById(R.id.containerHomeRelative);
//        openNavigationMenu(mapFragment);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    goOnlineRelative.setVisibility(View.GONE);
                    mapView.setVisibility(View.GONE);
                    containerHomeRelative.setVisibility(View.VISIBLE);
                    openNavigationMenu(pastOrderFragment);
                } else if (tab.getPosition() == 1) {
                    goOnlineRelative.setVisibility(View.VISIBLE);
                    mapView.setVisibility(View.VISIBLE);
                    containerHomeRelative.setVisibility(View.GONE);
//                    openNavigationMenu(mapFragment);
                } else if (tab.getPosition() == 2) {
                    goOnlineRelative.setVisibility(View.GONE);
                    mapView.setVisibility(View.GONE);
                    containerHomeRelative.setVisibility(View.VISIBLE);
                    openNavigationMenu(settingsFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void openNavigationMenu(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.containerHomeRelative, fragment).commit();
    }

//    @Override
//    public void onTaskDone(Object... values) {
//        Log.i("TAG", "TAG Main activity on task done is called");
////        mapFragment.loadPolyLine((PolylineOptions)values[0]);
//    }
    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {

        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);
                initSource(style);
                initLayers(style);
                getDriverCurrentOrder();
            }
        });

//        mapboxMap.addOnMapClickListener(this);

//        mapboxMap.setOnMarkerClickListener(this);
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
            locationComponent.setCameraMode(CameraMode.TRACKING);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @SuppressWarnings( {"MissingPermission"})
    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        return false;
    }

    private void initLayers(@NonNull Style loadedMapStyle) {
        LineLayer routeLayer = new LineLayer(ROUTE_LAYER_ID, ROUTE_SOURCE_ID);

        routeLayer.setProperties(
                lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineWidth(5f),
                lineColor(Color.parseColor("#009688"))
        );
        loadedMapStyle.addLayer(routeLayer);

    }

    private void getRoute(Point origin, Point destination) {
        client = MapboxDirections.builder()
                .origin(origin)
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .accessToken(Mapbox.getAccessToken())
                .build();

        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {

                Timber.d("Response code: " + response.code());
                if (response.body() == null) {
                    Timber.e("No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Timber.e("No routes found");
                    return;
                }

                currentRoute = response.body().routes().get(0);
                staticCurrentRoute = response.body().routes().get(0);
                initLocationEngine();

                /*Toast.makeText(MainActivity.this, String.format(
                        getString(R.string.directions_activity_toast_message),
                        currentRoute.distance()), Toast.LENGTH_SHORT).show();*/

                if (mapboxMap != null) {
                    mapboxMap.getStyle(new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {

                            GeoJsonSource source = style.getSourceAs(ROUTE_SOURCE_ID);


                            if (source != null) {
                                Timber.d("onResponse: source != null");
                                source.setGeoJson(FeatureCollection.fromFeature(
                                        Feature.fromGeometry(LineString.fromPolyline(currentRoute.geometry(), 6))));


                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                Timber.e("Error: " + throwable.getMessage());
                Toast.makeText(MainActivity.this, "Error: " + throwable.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSource(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(ROUTE_SOURCE_ID,
                FeatureCollection.fromFeatures(new Feature[] {})));

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }


    private static class MainActivityLocationCallback
            implements LocationEngineCallback<LocationEngineResult> {

        private final WeakReference<MainActivity> activityWeakReference;

        MainActivityLocationCallback(MainActivity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location has changed.
         *
         * @param result the LocationEngineResult object which has the last known location within it.
         */
        @Override
        public void onSuccess(LocationEngineResult result) {
            MainActivity activity = activityWeakReference.get();

            if (activity != null) {
                Location location = result.getLastLocation();

                if (location == null) {
                    return;
                }
//                + Math.random() * 20 + 1
                // Create a Toast which displays the new location's coordinates
               Log.i("TAG","TAG: HAri location "+result.getLastLocation());
                storeDriverLocationOnFirebase(new DriverLocationModel(result.getLastLocation().getLatitude(),result.getLastLocation().getLongitude() ,staticCurrentRoute.toJson()));
                // Pass the new location to the Maps SDK's LocationComponent
                if (activity.mapboxMap != null && result.getLastLocation() != null) {
                    activity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
                }
            }
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location can not be captured
         *
         * @param exception the exception message
         */
        @Override
        public void onFailure(@NonNull Exception exception) {
            Log.d("LocationChangeActivity", exception.getLocalizedMessage());
            MainActivity activity = activityWeakReference.get();
            if (activity != null) {
                Toast.makeText(activity, exception.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getUserToken() {
        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        String token = sharedPreferences.getString("ownerId", "");
        return token;
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
                                AddressModel model = driverCurrentRequestModel.getOrderId().getAddressId();
                                destination = Point.fromLngLat(Double.parseDouble(model.get_long()),Double.parseDouble(model.getLat()));
                                getRoute(origin,destination);
                                Log.i("TAG","TAG: Order id "+driverCurrentRequestModel.get_id());
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

    private void finishCurrentOrder() {
        if(driverCurrentRequestModel != null) {
            try {
                APIClient.getInstance().getApi().finishCurrentOrder(driverCurrentRequestModel.get_id(),GlobalObjects.delivered).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            int status = response.code();
                            if(status == 200) {
                                finishCardView.setVisibility(View.GONE);
                                acceptRejectLinear.setVisibility(View.VISIBLE);
                                newOrderCardView.setVisibility(View.GONE);
                            }
                        } catch (Exception ex) {
                            Log.i("TAG","TAG "+ex.getMessage());
                        }
                     }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("TAG","TAG: Server failure "+t.getMessage());
                    }
                });
            } catch (Exception ex) {
                Log.i("TAG","TAG "+ex.getMessage());
            }
        }
    }

    private void updateStatusOfCurrentOrder(String orderStatus) {
        try {
            APIClient.getInstance().getApi().updateCurrentOrderStatus(driverCurrentRequestModel.get_id(),orderStatus).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        int status = response.code();
                        if(status == 200) {
                            acceptRejectLinear.setVisibility(View.GONE);
                            finishCardView.setVisibility(View.VISIBLE);
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

    private static void storeDriverLocationOnFirebase(DriverLocationModel model) {
        if(!tokenId.isEmpty()) {
            FirebaseDatabase.getInstance().getReference("Drivers").child(tokenId).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Log.i("TAG","TAG: Successfully updated location on Firebase");
                    } else {
                        Log.i("TAG","TAG: Location firebase task in unsuccessful");
                    }
                }
            }).addOnCanceledListener(new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    Log.i("TAG","TAG: Failure of listener of firebase");
                }
            });
        }
    }


    private void changeDriverOnlineStatus(boolean isOnline) {
        String token = getUserToken();
        if(!token.isEmpty()) {
            try {
                APIClient.getInstance().getApi().updateOnlineStatus(token,isOnline).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            int status = response.code();
                            if(status == 200) {
                                if(isOnline) {
                                    statusTV.setText(getString(R.string.online));
                                } else {
                                    statusTV.setText(getString(R.string.offline));
                                }

                            }
                        } catch (Exception ex) {
                            Log.i("TAG","TAG "+ex.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("TAG","TAG Server failure: "+t.getMessage());
                    }
                });
            } catch (Exception ex) {
                Log.i("TAG","TAG "+ex.getMessage());
            }
        }
    }

}
