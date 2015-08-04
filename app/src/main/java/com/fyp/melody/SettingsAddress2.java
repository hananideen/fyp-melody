package com.fyp.melody;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by Hananideen on 4/8/2015.
 */
public class SettingsAddress2 extends AppCompatActivity {

    SharedPreferences settings;
    SharedPreferences.Editor editor;
    GoogleMap map;
    private EditText editHome, editStreet;
    private Button buttonConfirm, buttonCancel;
    private String home;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_address1);

        settings = getSharedPreferences(ApplicationLoader.Settings_PREFS_NAME, 0);
        editor = settings.edit();

        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        map = fm.getMap();
        map.setMyLocationEnabled(true);

        editHome = (EditText) findViewById(R.id.editHome);
        editHome.setText(settings.getString("Home2", ""));

        editStreet = (EditText) findViewById(R.id.editStreet);
        editStreet.setText(settings.getString("Street2", ""));

        buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                home = editHome.getText().toString();
                if (home.length()==0) {
                    Toast.makeText(getApplicationContext(), "Please insert your home number/building name.", Toast.LENGTH_LONG).show();
                }
                else {
                    editor.putString("Home2", editHome.getText().toString());
                    editor.putString("Street2", editStreet.getText().toString());
                    editor.commit();
                    finish();
                }
            }
        });

        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
