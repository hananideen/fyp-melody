package com.fyp.melody.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.fyp.melody.ApplicationLoader;
import com.fyp.melody.R;

/**
 * Created by Hananideen on 8/7/2015.
 */
public class SettingsAddress extends ActionBarActivity {

    private Button buttonMain, buttonDelivery;

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_address);

        settings = getSharedPreferences(ApplicationLoader.Settings_PREFS_NAME, 0);
        editor = settings.edit();

        buttonMain = (Button) findViewById(R.id.buttonMain);
        buttonMain.setText(settings.getString("Home", "") + ", " + settings.getString("Street", ""));
        buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsAddress.this, SettingsAddress1.class);
                startActivityForResult(intent, 1);
            }
        });

        buttonDelivery = (Button) findViewById(R.id.buttonDelivery);
        buttonDelivery.setText(settings.getString("Home2", "") + ", " + settings.getString("Street2", ""));
        buttonDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsAddress.this, SettingsAddress2.class);
                startActivityForResult(intent, 2);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                buttonMain.setText(settings.getString("Home", "") + ", " + settings.getString("Street", ""));

            } else if (requestCode == 2) {

                buttonDelivery.setText(settings.getString("Home2", "") + ", " + settings.getString("Street2", ""));

            }
        }
    }
}
