package com.adventurous.adventurous;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

public class PointsActivity extends FragmentActivity implements OnMapReadyCallback, IGetPointsTaskListener, LocationListener, SensorEventListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Point[] mPoints;
    private Location mMyLocation = null;
    private GPSTracker gpsTracker;
    private SensorManager mSensorManager;
    private Marker mPositionMarker;
    private Button mPlayButton;
    private Button mPayButton;
    private Map<Marker, Point> mMarkerToPoint;
    private Point mCurrentPoint;
    private Point mNonFreePoint;
    private Marker mNonFreeMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        gpsTracker = new GPSTracker(this);
        gpsTracker.start();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        mPlayButton = (Button)findViewById(R.id.play_button);
        mPlayButton.setVisibility(View.INVISIBLE);

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gpsTracker.stop();
                Intent directionActivity = new Intent(PointsActivity.this, DirectionActivity.class);
                directionActivity.putExtra("POINTS", mPoints);
                directionActivity.putExtra("ID", mCurrentPoint.id);
                startActivity(directionActivity);

                finish();
            }
        });

        mPayButton = (Button)findViewById(R.id.pay_button);
        mPayButton.setVisibility(View.INVISIBLE);

        mPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gpsTracker.stop();
                Intent directionActivity = new Intent(PointsActivity.this, PayActivity.class);
                startActivityForResult(directionActivity, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            mNonFreePoint.isFree = true;
            mNonFreeMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);

        mMap.setOnMarkerClickListener(this);

        mPoints = (Point[])getIntent().getSerializableExtra("POINTS");
        if(mPoints == null){
            GetPointsTask getPointsTask = new GetPointsTask(this);
            getPointsTask.start();
        }
        else{
            DrawPoints(mPoints);
        }
    }

    @Override
    public void notifyOfGetPointsTaskComplete(Thread thread, Point[] points) {

        mPoints = points;
        DrawPoints(points);
    }

    private void DrawPoints(Point[] points) {
        mMarkerToPoint = new HashMap<>();

        for (final Point p: mPoints) {
            final LatLng position = new LatLng(p.pointLatitude, p.pointLongitude);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(p.isFree){
                        mMarkerToPoint.put(mMap.addMarker(new MarkerOptions().position(position).title(p.clue).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))), p);
                    }
                    else{
                        mMarkerToPoint.put(mMap.addMarker(new MarkerOptions().position(position).title(p.clue)), p);
                    }
                }
            });
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng position = new LatLng(location.getLatitude(), location.getLongitude());

        if(mMyLocation == null){

            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 14.0f));
        }

        mMyLocation = location;

        if (mPositionMarker == null) {

            mPositionMarker = mMap.addMarker(new MarkerOptions()
                    .anchor(0.5f, 0.5f)
                    .position(position));
            //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.positionpointericon)));
        }

        mPositionMarker.setPosition(position);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float degree = Math.round(event.values[0]) + 180;

        if(mPositionMarker == null)
            return;
        mPositionMarker.setRotation(degree);
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

    @Override
    public boolean onMarkerClick(Marker marker) {

        mCurrentPoint = mMarkerToPoint.get(marker);
        if(mCurrentPoint.isFree){
            mPayButton.setVisibility(View.INVISIBLE);
            mPlayButton.setVisibility(View.VISIBLE);
        }
        else{
            mPlayButton.setVisibility(View.INVISIBLE);
            mPayButton.setVisibility(View.VISIBLE);

            mNonFreePoint = mCurrentPoint;
            mNonFreeMarker = marker;
        }
        return false;
    }
}
