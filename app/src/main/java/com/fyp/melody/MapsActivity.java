package com.fyp.melody;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends ActionBarActivity {

    private GoogleMap mMap;
    private String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.setMyLocationEnabled(true);

        Intent track = getIntent();
        latitude = track.getStringExtra("lat");
        longitude = track.getStringExtra("long");

        Double latDouble = Double.parseDouble(latitude);
        Double longDouble = Double.parseDouble(longitude);

        LatLng location = new LatLng(latDouble, longDouble);
        mMap.addMarker(new MarkerOptions().position(location).title("Your location"));
        CameraUpdate zoomLocation = CameraUpdateFactory.newLatLngZoom(location, 15);
        mMap.animateCamera(zoomLocation);
    }

}