package com.fyp.melody;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Hananideen on 4/8/2015.
 */
public class SettingsAddress1 extends AppCompatActivity {

    SharedPreferences settings;
    SharedPreferences.Editor editor;
    GoogleMap map;
    private EditText editHome, editStreet, editSearch;
    private Button buttonConfirm, buttonCancel, buttonSearch;
    private String home, search;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_address1);

        settings = getSharedPreferences(ApplicationLoader.Settings_PREFS_NAME, 0);
        editor = settings.edit();

        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        map = fm.getMap();
        map.setMyLocationEnabled(true);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {

                MarkerOptions options = new MarkerOptions();
                options.position(point);
                map.addMarker(options);

            }
        });

        editSearch = (EditText) findViewById(R.id.searchPlaces);

        buttonSearch = (Button) findViewById(R.id.SearchButton);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = editSearch.getText().toString();

                if (search.equals("MMU") || search.equals("Multimedia University") || search.equals("mmu") || search.equals("multimedia university")){

                    LatLng mmu = new LatLng(2.927, 101.641);
                    CameraUpdate mmuLocation = CameraUpdateFactory.newLatLngZoom(mmu, 15);
                    map.animateCamera(mmuLocation);
                    map.addMarker(new MarkerOptions().position(mmu).title("MMU"));

                }

            }
        });

        editHome = (EditText) findViewById(R.id.editHome);
        editHome.setText(settings.getString("Home", ""));

        editStreet = (EditText) findViewById(R.id.editStreet);
        editStreet.setText(settings.getString("Street", ""));

        buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                home = editHome.getText().toString();
                if (home.length()==0) {
                    Toast.makeText(getApplicationContext(), "Please insert your home number/building name.", Toast.LENGTH_LONG).show();
                }
                else {
                    editor.putString("Home", editHome.getText().toString());
                    editor.putString("Street", editStreet.getText().toString());
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
