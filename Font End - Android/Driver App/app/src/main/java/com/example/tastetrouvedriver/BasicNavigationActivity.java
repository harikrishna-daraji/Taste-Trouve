package com.example.tastetrouvedriver;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineGradient;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.tastetrouvedriver.Helper.GlobalObjects;
import com.google.android.gms.common.internal.service.Common;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.directions.v5.models.RouteOptions;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Polyline;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.navigation.core.MapboxNavigation;
import com.mapbox.navigation.core.directions.session.RoutesRequestCallback;
import com.mapbox.navigation.ui.NavigationViewOptions;
import com.mapbox.navigation.ui.map.NavigationMapboxMap;
import com.mapbox.services.android.navigation.ui.v5.NavigationView;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class BasicNavigationActivity extends AppCompatActivity implements
        OnMapReadyCallback, MapboxMap.OnMapLongClickListener
{

    MapView mapView;
    private MapboxMap mapboxMap;
    private ProgressBar route_retrieval_progress_spinner;
    MapboxNavigation mapboxNavigation;
    String ORIGIN_COLOR = "#32a852"; // Green
    String DESTINATION_COLOR = "#F84D4D"; // Red

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_basic_navigation);
        mapView = findViewById(R.id.mapView);
        route_retrieval_progress_spinner = findViewById(R.id.route_retrieval_progress_spinner);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        mapboxNavigation = new MapboxNavigation(MapboxNavigation.defaultNavigationOptionsBuilder(this,getString(R.string.mapbox_access_token)).build());
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                BasicNavigationActivity.this.mapboxMap = mapboxMap;
                enableLocationComponent();
                style.addSource(new GeoJsonSource("CLICK_SOURCE"));
                style.addSource(new GeoJsonSource("ROUTE_LINE_SOURCE_ID",new GeoJsonOptions().withLineMetrics(true)));
                style.addImage("ICON_ID", BitmapFactory.decodeResource(BasicNavigationActivity.this.getResources(), R.drawable.mapbox_marker_icon_default));
                style.addLayerBelow(new LineLayer("ROUTE_LAYER_ID", "ROUTE_LINE_SOURCE_ID").withProperties(lineCap(Property.LINE_CAP_ROUND),lineJoin(Property.LINE_JOIN_ROUND),lineWidth(6f),lineGradient(Expression.interpolate(Expression.linear(),Expression.lineProgress(),Expression.stop(0f,Expression.color(Color.parseColor(ORIGIN_COLOR))),Expression.stop(1f,Expression.color(Color.parseColor(DESTINATION_COLOR)))))),"mapbox-location-shadow-layer");
                style.addLayerAbove(new SymbolLayer("CLICK_LAYER","CLICK_SOURCE").withProperties(iconImage("ICON_ID")),"ROUTE_LAYER_ID");
                mapboxMap.addOnMapLongClickListener(BasicNavigationActivity.this);
                GlobalObjects.Toast(getBaseContext(),"Long press on the map to navigate");
            }
        });
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent() {
        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                LocationComponent locationComponent = mapboxMap.getLocationComponent();
                locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(BasicNavigationActivity.this,style).build());
                locationComponent.setLocationComponentEnabled(true);
                locationComponent.setCameraMode(CameraMode.TRACKING);
                locationComponent.setRenderMode(RenderMode.COMPASS);

            }
        });
    }

    @Override
    public boolean onMapLongClick(@NonNull LatLng point) {
        route_retrieval_progress_spinner.setVisibility(View.VISIBLE);
        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                GeoJsonSource clickPointSource = style.getSourceAs("CLICK_SOURCE");
                clickPointSource.setGeoJson(Point.fromLngLat(point.getLongitude(),point.getLatitude()));
            }
        });

        Location location = mapboxMap.getLocationComponent().getLastKnownLocation();
        if(location != null) {
            List<Point> coordinates = new ArrayList<Point>();
            coordinates.add(Point.fromLngLat(location.getLongitude(),location.getLongitude()));
            coordinates.add(null);
            coordinates.add(Point.fromLngLat(point.getLongitude(),point.getLongitude()));
            mapboxNavigation.requestRoutes(RouteOptions.builder().baseUrl("").accessToken(getString(R.string.mapbox_access_token)).coordinates(coordinates).alternatives(true).profile(DirectionsCriteria.PROFILE_DRIVING_TRAFFIC).build(), new RoutesRequestCallback() {
                @Override
                public void onRoutesReady(@NonNull List<? extends DirectionsRoute> list) {
                    if(!list.isEmpty()) {
                        GlobalObjects.Toast(getBaseContext(),"Found the route");
                        mapboxMap.getStyle(new Style.OnStyleLoaded() {
                            @Override
                            public void onStyleLoaded(@NonNull Style style) {
                                GeoJsonSource clickPointSource = style.getSourceAs("ROUTE_LINE_SOURCE_ID");
                                LineString polyline = LineString.fromPolyline(list.get(0).geometry(),6);
                                clickPointSource.setGeoJson(polyline);
                            }
                        });
                        route_retrieval_progress_spinner.setVisibility(View.INVISIBLE);
                    } else {
                        GlobalObjects.Toast(getBaseContext(),"No routes were found");
                    }
                }

                @Override
                public void onRoutesRequestFailure(@NonNull Throwable throwable, @NonNull RouteOptions routeOptions) {
                    Timber.e("route request failure %s", throwable.toString());
                    GlobalObjects.Toast(getBaseContext(),"Failure in finding route");
                }

                @Override
                public void onRoutesRequestCanceled(@NonNull RouteOptions routeOptions) {
                    Timber.d("route request canceled");
                }
            });
        }
        return true;
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
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}