package com.laacompany.travelplanner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.laacompany.travelplanner.Handle.Handle;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener {

    private MapView mMapView;
    private MapboxMap mMapboxMap;
    private PermissionsManager mPermissionsManager;
    private LocationComponent mLocationComponent;
    private DirectionsRoute currentRoute;
    private ArrayList<DirectionsRoute> routelist = new ArrayList<>();
    private NavigationMapRoute mNavigationMapRoute;


    private int currentIndex = -1;
    private boolean isExit = false;
    private Point originPoint, destinationPoint;

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext, MapActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_map);

        mMapView = findViewById(R.id.id_activity_map_map_view);
        mMapView.onCreate(savedInstanceState);


        mMapView.getMapAsync(this);
    }


    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        mMapboxMap = mapboxMap;

        mMapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);
                addDestinationIconLayoutStart(style);
                addDestinationIconLayoutEnd(style);
                mMapboxMap.addOnMapClickListener(MapActivity.this);
            }
        });

    }

    private void addDestinationIconLayoutStart(Style style) {
        style.addImage("destination-icon-id-start", BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));

        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id-start");
        style.addSource(geoJsonSource);

        SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id-start", "destination-source-id-start");
        destinationSymbolLayer.withProperties(iconImage("destination-icon-id-start"), iconAllowOverlap(true), iconIgnorePlacement(true));

        style.addLayer(destinationSymbolLayer);
    }

    private void addDestinationIconLayoutEnd(Style style) {
        style.addImage("destination-icon-id-end", BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));

        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id-end");
        style.addSource(geoJsonSource);

        SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id-end", "destination-source-id-end");
        destinationSymbolLayer.withProperties(iconImage("destination-icon-id-end"), iconAllowOverlap(true), iconIgnorePlacement(true));

        style.addLayer(destinationSymbolLayer);
    }

    private void enableLocationComponent(@NotNull Style loadedMapStyle) {
        if(PermissionsManager.areLocationPermissionsGranted(this)){
            mLocationComponent = mMapboxMap.getLocationComponent();
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                return;
            }
            mLocationComponent.activateLocationComponent(this, loadedMapStyle);
            mLocationComponent.setLocationComponentEnabled(true);

            mLocationComponent.setCameraMode(CameraMode.TRACKING);
        } else {
            mPermissionsManager = new PermissionsManager(this);
            mPermissionsManager.requestLocationPermissions(this);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPermissionsManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        Point originPoint = Point.fromLngLat(mLocationComponent.getLastKnownLocation().getLongitude(), mLocationComponent.getLastKnownLocation().getLatitude());
        Point destinationPoint = Point.fromLngLat(point.getLongitude(),point.getLatitude());

        GeoJsonSource sourceStart = mMapboxMap.getStyle().getSourceAs("destination-source-id-start");
        GeoJsonSource sourceDestination = mMapboxMap.getStyle().getSourceAs("destination-source-id-end");

        if (sourceStart != null && sourceDestination != null){
            sourceStart.setGeoJson(Feature.fromGeometry(originPoint));
            sourceDestination.setGeoJson(Feature.fromGeometry(destinationPoint));
        }

        getRoute(originPoint, destinationPoint);

        return true;
    }

//    private Runnable simulateRoute = new Runnable() {
//        @Override
//        public void run() {
//            Log.d("123", currentIndex + " " + Handle.sCurrentRoutes.size());
//            if(isExit) return;
//            if(currentIndex == Handle.sCurrentRoutes.size()){
//                hideMarker();
//                mNavigationMapRoute.removeRoute();
//                currentIndex = -1;
//            } else {
//
//                int idto = (currentIndex+1) % Handle.sCurrentRoutes.size();
//                Pair<Double,Double> originPair = Handle.sCurrentRoutes.get(currentIndex), destinationPair = Handle.sCurrentRoutes.get(idto);
//                originPoint = Point.fromLngLat(originPair.second, originPair.first);
//                destinationPoint = Point.fromLngLat(destinationPair.second, destinationPair.first);
//
//                if (routelist.size() == Handle.sCurrentRoutes.size()){
//                    mNavigationMapRoute.addRoute(routelist.get(currentIndex));
//                    showMarker();
//                    new Handler().postDelayed(simulateRoute, 3000);
//                } else {
//                    getRoute(originPoint, destinationPoint);
//                }
//                currentIndex++;
//            }
//        }
//    };

//    private void showMarker(){
//        GeoJsonSource sourceStart = mMapboxMap.getStyle().getSourceAs("destination-source-id-start");
//        GeoJsonSource sourceDestination = mMapboxMap.getStyle().getSourceAs("destination-source-id-end");
//
//        if (sourceStart != null && sourceDestination != null){
//            sourceStart.setGeoJson(Feature.fromGeometry(originPoint));
//            sourceDestination.setGeoJson(Feature.fromGeometry(destinationPoint));
//        }
//    }
//
//    private void hideMarker(){
//        GeoJsonSource sourceStart = mMapboxMap.getStyle().getSourceAs("destination-source-id-start");
//        GeoJsonSource sourceDestination = mMapboxMap.getStyle().getSourceAs("destination-source-id-end");
//
//        if (sourceStart != null && sourceDestination != null){
//            sourceStart.setGeoJson(Feature.fromGeometry(Point.fromLngLat(0,0)));
//            sourceDestination.setGeoJson(Feature.fromGeometry(Point.fromLngLat(0,0)));
//        }
//    }


    private void getRoute(Point originPoint, Point destinationPoint) {
        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(originPoint)
                .destination(destinationPoint)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        if(isExit) return;
                        if(response.body() != null && response.body().routes().size() > 0){
                            currentRoute = response.body().routes().get(0);
                            Log.d("123", response.body().routes().get(0).toString());

                            if(mNavigationMapRoute != null){
                                mNavigationMapRoute.removeRoute();
                            } else {
                                mNavigationMapRoute = new NavigationMapRoute(null, mMapView,mMapboxMap, R.style.NavigationMapRoute);
                            }

                            mNavigationMapRoute.addRoute(currentRoute);
//                            routelist.add(currentRoute);
//                            showMarker();
//                            new Handler().postDelayed(simulateRoute, 1000);
                        }

                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {
        if(granted){
            enableLocationComponent(mMapboxMap.getStyle());
        } else {
            Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public void clickStartNavigation(View view) {
        boolean simulateRoute = true;
        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                .directionsRoute(currentRoute)
                .shouldSimulateRoute(simulateRoute)
                .build();

        NavigationLauncher.startNavigation(this, options);

    }

    public void clickStartSimulate(View view) {
//        if(currentIndex == -1){
//            currentIndex = 0;
//            new Handler().postDelayed(simulateRoute, 0);
//        }
    }

    @Override
    public void onBackPressed() {
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        Future longRunningTaskFuture = executorService.submit(simulateRoute);
//        longRunningTaskFuture.cancel(true);
        isExit = true;
        super.onBackPressed();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

}
