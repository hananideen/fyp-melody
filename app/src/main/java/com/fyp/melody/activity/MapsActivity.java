package com.fyp.melody.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fyp.melody.ApplicationLoader;
import com.fyp.melody.JSON.DirectionsJSONParser;
import com.fyp.melody.JSON.Json2Deliveryman;
import com.fyp.melody.JSON.Json2Location;
import com.fyp.melody.JSON.Json2Tracking;
import com.fyp.melody.R;
import com.fyp.melody.VolleySingleton;
import com.fyp.melody.model.Deliveryman;
import com.fyp.melody.model.Location;
import com.fyp.melody.model.Tracking;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends ActionBarActivity {

    private GoogleMap mMap;
    private String latitude, longitude;
    private double latServer, longServer;
    private TextView tvDistanceDuration, dName, dPhone, dPlate;
    Handler mHandler;
    LatLng delivery, location;
    Marker driver;
    Polyline line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        this.mHandler = new Handler();
        this.mHandler.postDelayed(m_Runnable, 5000);

        tvDistanceDuration = (TextView) findViewById(R.id.tvDistanceTime);
        dName = (TextView) findViewById(R.id.textViewDName);
        dPhone = (TextView) findViewById(R.id.textViewDContact);
        dPlate = (TextView) findViewById(R.id.textViewDPlat);

        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.setMyLocationEnabled(true);

        Intent track = getIntent();
        latitude = track.getStringExtra("lat");
        longitude = track.getStringExtra("long");

        Double latDouble = Double.parseDouble(latitude);
        Double longDouble = Double.parseDouble(longitude);

        location = new LatLng(latDouble, longDouble);
        mMap.addMarker(new MarkerOptions().position(location).title("Your location " +latitude
                + ", " +longitude)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker_finish)));
        CameraUpdate zoomLocation = CameraUpdateFactory.newLatLngZoom(location, 15);
        mMap.animateCamera(zoomLocation);

//        delivery = new LatLng(2.923615, 101.662622);
//        mMap.addMarker(new MarkerOptions().position(delivery).title("Deliveryman")
//                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.melody_logo)));

        JsonArrayRequest locationRequest = new JsonArrayRequest(ApplicationLoader.getIp("restaurant/coordinate.php"), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Location coordinates = new Location(new Json2Location(obj));
                        latServer = coordinates.getLatitude();
                        longServer = coordinates.getLongitude();
                        delivery = new LatLng(latServer, longServer);
                        driver = mMap.addMarker(new MarkerOptions().position(delivery).title("Deliveryman " + String.format("%.3f", latServer)
                                + ", " + String.format("%.3f", longServer))
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.melody_logo)));
                        LatLng destination = location;
                        LatLng origin = delivery;

                        String url = getDirectionsUrl(destination, origin);

                        DownloadTask downloadTask = new DownloadTask();

                        downloadTask.execute(url);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VolleyServer", "Error: " + error.getMessage());
                Toast.makeText(getApplication(), "Oops! Have you checked your internet connection?", Toast.LENGTH_SHORT).show();
            }
        });

        VolleySingleton.getInstance().getRequestQueue().add(locationRequest);

        JsonArrayRequest trackingRequest = new JsonArrayRequest(ApplicationLoader.getIp("restaurant/deliveryman.php"), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Deliveryman deliveryman = new Deliveryman(new Json2Deliveryman(obj));
                        dName.setText(deliveryman.getName());
                        dPhone.setText(deliveryman.getPhone());
                        dPlate.setText(deliveryman.getPlate());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VolleyServer", "Error: " + error.getMessage());
                Toast.makeText(getApplication(), "Oops! Have you checked your internet connection?", Toast.LENGTH_SHORT).show();
            }
        });

        VolleySingleton.getInstance().getRequestQueue().add(trackingRequest);

    }

    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            driver.remove();
            line.remove();

            JsonArrayRequest locationRequest = new JsonArrayRequest(ApplicationLoader.getIp("restaurant/coordinate.php"), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Location coordinates = new Location(new Json2Location(obj));
                            latServer = coordinates.getLatitude();
                            longServer = coordinates.getLongitude();
                            delivery = new LatLng(latServer, longServer);
                            driver = mMap.addMarker(new MarkerOptions().position(delivery).title("Deliveryman " + String.format("%.3f", latServer)
                                    + ", " + String.format("%.3f", longServer))
                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.melody_logo)));
                            LatLng destination = location;
                            LatLng origin = delivery;

                            String url = getDirectionsUrl(destination, origin);
                            DownloadTask downloadTask = new DownloadTask();
                            downloadTask.execute(url);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("VolleyServer", "Error: " + error.getMessage());
                    Toast.makeText(getApplication(), "Oops! Have you checked your internet connection?", Toast.LENGTH_SHORT).show();
                }
            });

            VolleySingleton.getInstance().getRequestQueue().add(locationRequest);

            MapsActivity.this.mHandler.postDelayed(m_Runnable, 5000);
        }
    };

    private String getDirectionsUrl(LatLng destination ,LatLng origin){

        String str_destination = "destination="+destination.latitude+","+destination.longitude;
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        String sensor = "sensor=false";
        String parameters = str_destination+"&"+str_origin+"&"+sensor;

        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            String distance = "";
            String duration = "";

            if(result.size()<1){
                Toast.makeText(getBaseContext(), "No Points", Toast.LENGTH_SHORT).show();
                return;
            }

            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    if(j==0){
                        distance = (String)point.get("distance");
                        continue;
                    }else if(j==1){
                        duration = (String)point.get("duration");
                        continue;
                    }

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(2);
                lineOptions.color(Color.RED);
            }

            tvDistanceDuration.setText("ETA: "+duration +", Distance: "+distance );

            line = mMap.addPolyline(lineOptions);
        }
    }

    public void onBackPressed (){
    }

}