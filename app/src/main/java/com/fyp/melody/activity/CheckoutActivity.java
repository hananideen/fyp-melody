package com.fyp.melody.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.fyp.melody.ApplicationLoader;
import com.fyp.melody.R;

/**
 * Created by Hananideen on 30/7/2015.
 */
public class CheckoutActivity extends Activity {

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    private EditText editName, editPass;
    private Button buttonConfirm;
    private String password, savedPassword, home, street, home2, street2, savedLatitude, savedLongitude, savedLatitude2, savedLongitude2, total;
    private RadioButton address1, address2;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        settings = getSharedPreferences(ApplicationLoader.Settings_PREFS_NAME, 0);
        editor = settings.edit();

        Intent intent = getIntent();
        total = intent.getStringExtra("subtotal");
        //Toast.makeText(getApplicationContext(), ""+total, Toast.LENGTH_LONG).show();

        editName = (EditText) findViewById(R.id.ProfileUsername);
        editName.setText(settings.getString("userName", ""));

        editPass = (EditText) findViewById(R.id.editPass);

        address1 = (RadioButton) findViewById(R.id.radio1);
        address1.setText("Main Address: " + settings.getString("Home", "") + ", " + settings.getString("Street", ""));
        address1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(CheckoutActivity.this, SettingsAddress1.class);
                startActivityForResult(add, 1);
            }
        });

        address2 = (RadioButton) findViewById(R.id.radio2);
        address2.setText("Delivery Address: " + settings.getString("Home2", "") + ", " + settings.getString("Street2", "")
                + "(Change this address if your delivery address is not listed)");
        address2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(CheckoutActivity.this, SettingsAddress2.class);
                startActivityForResult(add, 2);
            }
        });

        savedPassword = settings.getString("Password", "");

        buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password = editPass.getText().toString();
                if (password.equals(savedPassword)) {
                    editor.putString("userName", editName.getText().toString());
                    editor.commit();

                    Intent track = new Intent(CheckoutActivity.this, TrackingActivity.class);
                    if (address1.isChecked()){
                        track.putExtra("home", home);
                        track.putExtra("street", street);
                        track.putExtra("lat", savedLatitude);
                        track.putExtra("long", savedLongitude);
                    } else {
                        track.putExtra("home", home2);
                        track.putExtra("street", street2);
                        track.putExtra("lat", savedLatitude2);
                        track.putExtra("long", savedLongitude2);
                    }
                    track.putExtra("subtotal", total);
                    Toast.makeText(getApplicationContext(), "Order submitted", Toast.LENGTH_LONG).show();
                    startActivity(track);


                } else if (password.length()==0) {
                    Toast.makeText(getApplicationContext(), "Please insert your password", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "The password is incorrect", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                address1.setText("Main Address: " + settings.getString("Home", "") + ", " + settings.getString("Street", ""));
                home = settings.getString("Home", "");
                street = settings.getString("Street", "");
                savedLatitude = settings.getString("Latitude", "");
                savedLongitude = settings.getString("Longitude", "");

            } else if (requestCode == 2) {

                address2.setText("Delivery Address: " + settings.getString("Home2", "") + ", " + settings.getString("Street2", "")
                        + "(Change this address if your delivery address is not listed)");
                home2 = settings.getString("Home2", "");
                street2 = settings.getString("Street2", "");
                savedLatitude2 = settings.getString("Latitude2", "");
                savedLongitude2 = settings.getString("Longitude2", "");

            }
        }
    }
}
