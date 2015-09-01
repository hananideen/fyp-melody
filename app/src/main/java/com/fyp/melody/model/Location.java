package com.fyp.melody.model;

import com.fyp.melody.JSON.Json2Location;

/**
 * Created by Hananideen on 2/9/2015.
 */
public class Location {

    public double Latitude;
    public double Longitude;

    public Location() {}

    public Location (double latitude, double longitude) {
        Latitude = latitude;
        Longitude = longitude;
    }

    public Location (Json2Location jLocation){
        Latitude = jLocation.latitude;
        Longitude = jLocation.longitude;
    }

    public double getLatitude(){
        return Latitude;
    }

    public void setLatitude(double latitude){
        Latitude = latitude;
    }

    public double getLongitude(){
        return Longitude;
    }

    public void setLongitude(int longitude){
        Longitude = longitude;
    }
}
