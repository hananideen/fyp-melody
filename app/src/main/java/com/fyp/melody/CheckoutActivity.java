package com.fyp.melody;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Hananideen on 30/7/2015.
 */
public class CheckoutActivity extends AppCompatActivity {

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    private EditText editName, editHome, editStreet, editPostcode, editCity, editPass;
    private Button buttonConfirm;
    private String password, savedPassword;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        settings = getSharedPreferences(ApplicationLoader.Settings_PREFS_NAME, 0);
        editor = settings.edit();

        editName = (EditText) findViewById(R.id.ProfileUsername);
        editName.setText(settings.getString("userName", ""));

        editHome = (EditText) findViewById(R.id.editHome);
        editHome.setText(settings.getString("Home", ""));

        editStreet = (EditText) findViewById(R.id.editStreet);
        editStreet.setText(settings.getString("Street", ""));

        editPostcode = (EditText) findViewById(R.id.editPostcode);
        editPostcode.setText(settings.getString("Postcode", ""));

        editCity = (EditText) findViewById(R.id.editCity);
        editCity.setText(settings.getString("City", ""));

        editPass = (EditText) findViewById(R.id.editPass);


        savedPassword = settings.getString("Password", "");

        buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password = editPass.getText().toString();
                if (password.equals(savedPassword)) {
                    editor.putString("userName", editName.getText().toString());
                    editor.putString("Home", editHome.getText().toString());
                    editor.putString("Street", editStreet.getText().toString());
                    editor.putString("Postcode", editPostcode.getText().toString());
                    editor.putString("City", editCity.getText().toString());
                    editor.commit();

                    Intent track = new Intent(CheckoutActivity.this, TrackingActivity.class);
                    startActivity(track);

                } else if (password == null) {
                    Toast.makeText(getApplicationContext(), "Please insert your password.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "The password is incorrect", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
