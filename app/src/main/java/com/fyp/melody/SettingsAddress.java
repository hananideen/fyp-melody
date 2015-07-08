package com.fyp.melody;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Hananideen on 8/7/2015.
 */
public class SettingsAddress extends ActionBarActivity {

    private EditText editHome, editStreet, editPostcode, editCity;
    private Button buttonSave;

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_address);

        settings = getSharedPreferences(ApplicationLoader.Settings_PREFS_NAME, 0);
        editor = settings.edit();

        editHome = (EditText) findViewById(R.id.editHome);
        editHome.setText(settings.getString("Home", ""));

        editStreet = (EditText) findViewById(R.id.editStreet);
        editStreet.setText(settings.getString("Street", ""));

        editPostcode = (EditText) findViewById(R.id.editPostcode);
        editPostcode.setText(settings.getString("Postcode", ""));

        editCity = (EditText) findViewById(R.id.editCity);
        editCity.setText(settings.getString("City", ""));

        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editHome == null && editStreet == null && editPostcode == null && editCity == null){
                    Toast.makeText(getApplicationContext(), "Please insert your address.", Toast.LENGTH_LONG).show();
                }

                else {
                    editor.putString("Home", editHome.getText().toString());
                    editor.putString("Street", editStreet.getText().toString());
                    editor.putString("Postcode", editPostcode.getText().toString());
                    editor.putString("City", editCity.getText().toString());
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Address saved.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
