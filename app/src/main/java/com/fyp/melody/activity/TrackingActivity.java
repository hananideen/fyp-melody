package com.fyp.melody.activity;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.melody.ApplicationLoader;
import com.fyp.melody.R;

/**
 * Created by Hananideen on 30/6/2015.
 */


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")

public class TrackingActivity extends ActionBarActivity {

    Button btnStart, btnMap;
    TextView textViewTime, textViewName, textViewAddress1, textViewAddress2;
    SharedPreferences settings;
    TextView timestamp, ETA;
    String total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        settings = getSharedPreferences(ApplicationLoader.Settings_PREFS_NAME, 0);

        btnMap = (Button) findViewById(R.id.btnMap);
        textViewName = (TextView) findViewById(R.id.textName);
        textViewAddress1 = (TextView) findViewById(R.id.textAddress1);
        textViewAddress2 = (TextView) findViewById(R.id.textAddress2);
        timestamp = (TextView) findViewById(R.id.textViewTimestamp);
        ETA = (TextView) findViewById(R.id.textViewETA);

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
                map.putExtra("lat",latitude);
                map.putExtra("long",longitude);
                startActivity(map);
            }
        });



//        btnStart = (Button) findViewById(R.id.btnStart);
//        textViewTime = (TextView) findViewById(R.id.textViewTime);
//        textViewTime.setText("01:00:00");
//        final CounterClass timer = new CounterClass(3600000, 1000);
//        btnStart.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                timer.start();
//            }
//        });timer.start();


    }

//    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
//    @SuppressLint("NewApi")
//    public class CounterClass extends CountDownTimer {
//
//        public CounterClass(long millisInFuture, long countDownInterval) {
//            super(millisInFuture, countDownInterval);
//        }
//
//        @SuppressLint("NewApi")
//        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
//        @Override
//        public void onTick(long millisUntilFinished) {
//
//            long millis = millisUntilFinished;
//            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
//                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
//                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
//            System.out.println(hms);
//            textViewTime.setText(hms);
//        }
//
//        @Override
//        public void onFinish() {
//            textViewTime.setText("Completed.");
//        }
//
//
//
//    }


}
