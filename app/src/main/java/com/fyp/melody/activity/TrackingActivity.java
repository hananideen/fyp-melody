package com.fyp.melody.activity;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fyp.melody.ApplicationLoader;
import com.fyp.melody.JSON.Json2Tracking;
import com.fyp.melody.R;
import com.fyp.melody.VolleySingleton;
import com.fyp.melody.model.Tracking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hananideen on 30/6/2015.
 */

public class TrackingActivity extends ActionBarActivity {

    Button btnStart, btnMap;
    TextView textViewTime, textViewName, textViewAddress1, textViewAddress2;
    SharedPreferences settings;
    TextView timestamp, ETA;
    String total, status;
    ImageView tracking;
    Handler mHandler;
    RelativeLayout deliveryman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        this.mHandler = new Handler();
        this.mHandler.postDelayed(m_Runnable, 5000);

        settings = getSharedPreferences(ApplicationLoader.Settings_PREFS_NAME, 0);

        btnMap = (Button) findViewById(R.id.btnMap);
        textViewName = (TextView) findViewById(R.id.textName);
        textViewAddress1 = (TextView) findViewById(R.id.textAddress1);
        textViewAddress2 = (TextView) findViewById(R.id.textAddress2);
        timestamp = (TextView) findViewById(R.id.textViewTimestamp);
        ETA = (TextView) findViewById(R.id.textViewETA);
        tracking = (ImageView) findViewById(R.id.imageTracking);
        deliveryman = (RelativeLayout) findViewById(R.id.layoutDelivery);

        Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR);
        int minutes = c.get(Calendar.MINUTE);
        int AMPM = c.get(Calendar.AM_PM);
        try{
            if (AMPM == 1)
            {
                String PM = "";
                PM = "PM";
                timestamp.setText("Order Time: " + hours + ":" + minutes + PM);
            }
            else if (AMPM == 0)
            {
                String AM = "";
                AM = "AM";
                timestamp.setText("Order Time: " + hours + ":" + minutes + AM);
            }
        } catch (Exception e) {
        }

        try{
            if (AMPM == 1)
            {
                String PM = "";
                PM = "PM";
                hours = hours + 1;
                if(hours > 12)
                {
                    hours = hours - 12;
                    ETA.setText("ETA: " + hours + ":" + minutes + PM);
                } else {
                    ETA.setText("ETA: " + hours + ":" + minutes + PM);
                }

            }
            else if (AMPM == 0)
            {
                String AM = "";
                AM = "AM";
                hours = hours + 1;
                if(hours > 12)
                {
                    hours = hours - 12;
                    ETA.setText("ETA: " + hours + ":" + minutes + AM);
                } else {
                    ETA.setText("ETA: " + hours + ":" + minutes + AM);
                }
            }
        } catch (Exception e) {
        }

        Intent track = getIntent();
        String home = track.getStringExtra("home");
        String street = track.getStringExtra("street");
        final String latitude = track.getStringExtra("lat");
        final String longitude = track.getStringExtra("long");
        total = track.getStringExtra("subtotal");
        Toast.makeText(getApplicationContext(), ""+total, Toast.LENGTH_LONG).show();

        textViewName.setText(settings.getString("userName", ""));
        textViewAddress1.setText(home);
        textViewAddress2.setText(street);

        btnMap.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent map = new Intent(TrackingActivity.this, MapsActivity.class);
                map.putExtra("lat", latitude);
                map.putExtra("long", longitude);
                startActivity(map);
            }
        });

        JsonArrayRequest trackingRequest = new JsonArrayRequest(ApplicationLoader.getIp("restaurant/order-status.php"), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Tracking tracking = new Tracking(new Json2Tracking(obj));
                        status = tracking.getStatus();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VolleyServer", "Error: " + error.getMessage());
                Toast.makeText(getApplication(), "Cannot connect to server", Toast.LENGTH_SHORT).show();
            }
        });

        VolleySingleton.getInstance().getRequestQueue().add(trackingRequest);


    }

    private final Runnable m_Runnable = new Runnable() {
        public void run() {

            JsonArrayRequest trackingRequest = new JsonArrayRequest(ApplicationLoader.getIp("restaurant/order-status.php"), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Tracking tracking = new Tracking(new Json2Tracking(obj));
                            status = tracking.getStatus();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("VolleyServer", "Error: " + error.getMessage());
                    Toast.makeText(getApplication(), "Cannot connect to server", Toast.LENGTH_SHORT).show();
                }
            });

            VolleySingleton.getInstance().getRequestQueue().add(trackingRequest);

            if(status.equals("cooking")) {
                tracking.setImageResource(R.drawable.tracking2);
            }else if (status.equals("packing")){
                tracking.setImageResource(R.drawable.tracking3);
            }else if (status.equals("preparing")){
                tracking.setImageResource(R.drawable.tracking4);
                deliveryman.setVisibility(View.VISIBLE);
            }else if (status.equals("delivering")){
                tracking.setImageResource(R.drawable.tracking5);
                btnMap.setVisibility(View.VISIBLE);
            }else{
                tracking.setImageResource(R.drawable.tracking1);
            }
            TrackingActivity.this.mHandler.postDelayed(m_Runnable, 5000);
        }
    };

}
