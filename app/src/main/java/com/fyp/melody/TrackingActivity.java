package com.fyp.melody;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Hananideen on 30/6/2015.
 */


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")

public class TrackingActivity extends ActionBarActivity {

    Button btnStart, btnMap;
    TextView textViewTime, textViewName, textViewAddress1, textViewAddress2, textViewAddress3, textViewAddress4;
    SharedPreferences settings;
    TextView timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        settings = getSharedPreferences(ApplicationLoader.Settings_PREFS_NAME, 0);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnMap = (Button) findViewById(R.id.btnMap);
//        textViewTime = (TextView) findViewById(R.id.textViewTime);
        textViewName = (TextView) findViewById(R.id.textName);
        textViewAddress1 = (TextView) findViewById(R.id.textAddress1);
        textViewAddress2 = (TextView) findViewById(R.id.textAddress2);
        textViewAddress3 = (TextView) findViewById(R.id.textAddress3);
        textViewAddress4 = (TextView) findViewById(R.id.textAddress4);
        timestamp = (TextView) findViewById(R.id.textViewTimestamp);

//        textViewTime.setText("01:00:00");

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

        textViewName.setText(settings.getString("userName", ""));
        textViewAddress1.setText(settings.getString("Home", ""));
        textViewAddress2.setText(settings.getString("Street", ""));
        textViewAddress3.setText(settings.getString("Postcode", ""));
        textViewAddress4.setText(settings.getString("City", ""));


//        final CounterClass timer = new CounterClass(3600000, 1000);
//        btnStart.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                timer.start();
//            }
//        });timer.start();

        btnMap.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent map = new Intent(TrackingActivity.this, MapsActivity.class);
                startActivity(map);
            }
        });


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
