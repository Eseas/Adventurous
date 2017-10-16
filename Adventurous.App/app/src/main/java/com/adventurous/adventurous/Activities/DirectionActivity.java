package com.adventurous.adventurous.Activities;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.adventurous.adventurous.GPSTracker;
import com.adventurous.adventurous.Entities.Point;
import com.adventurous.adventurous.R;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.UUID;

public class DirectionActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, SensorEventListener {

    private GoogleMap mMap;
    private Point mPoint;
    private Location mMyLocation;
    private Marker mPositionMarker;
    private GPSTracker mGPSTracker;
    private SensorManager mSensorManager;
    private Location mPointLocation;
    private Point[] mPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


        mPoints = (Point[])getIntent().getSerializableExtra("POINTS");
        UUID id = (UUID) getIntent().getSerializableExtra("ID");

        for (Point p : mPoints){
            if( p.id.equals(id)){
                mPoint = p;
            }
        }


        mGPSTracker = new GPSTracker(this);
        mGPSTracker.start();

        mPointLocation = new Location("reverseGeocoded");
        mPointLocation.setLatitude(mPoint.pointLatitude);
        mPointLocation.setLongitude(mPoint.pointLongitude);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(new LatLng(mPoint.pointLatitude, mPoint.pointLongitude)).title(mPoint.clue));

        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng position = new LatLng(location.getLatitude(), location.getLongitude());

        if(mMyLocation == null){

            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 14.0f));
        }

        mMyLocation = location;

//        if (mPositionMarker == null) {
//
//            mPositionMarker = mMap.addMarker(new MarkerOptions()
//                    .anchor(0.5f, 0.5f)
//                    .position(position));
//            //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.positionpointericon)));
//        }
//
//        mPositionMarker.setPosition(position);
        float distance = location.distanceTo(mPointLocation);
        Log.d("DirectionActivity", "distance: " + distance);
        if(distance < 50) { // TODO make configurable

            mGPSTracker.stop();

            Intent gameActivity = new Intent(DirectionActivity.this, GameActivity.class);
            gameActivity.putExtra("POINTS", mPoints);
            gameActivity.putExtra("ID", mPoint.id);
            startActivity(gameActivity);

            finish();
            return;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        float degree = Math.round(event.values[0]) + 180;
//
//        if(mPositionMarker == null)
//            return;
//        mPositionMarker.setRotation(degree);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
