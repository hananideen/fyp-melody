package com.fyp.melody;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by Hananideen on 30/7/2015.
 */
public class CheckoutActivity extends Activity {

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    private EditText editName, editPass;
    private Button buttonConfirm;
    private String password, savedPassword;
    private RadioButton address1, address2;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        settings = getSharedPreferences(ApplicationLoader.Settings_PREFS_NAME, 0);
        editor = settings.edit();

        editName = (EditText) findViewById(R.id.ProfileUsername);
        editName.setText(settings.getString("userName", ""));

        editPass = (EditText) findViewById(R.id.editPass);

        address1 = (RadioButton) findViewById(R.id.radio1);
        address1.setText("Main Address: " + settings.getString("Home", ""));
        address1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(CheckoutActivity.this, SettingsAddress1.class);
                startActivity(add);
            }
        });

        address2 = (RadioButton) findViewById(R.id.radio2);
        address2.setText("Delivery Address: " + settings.getString("Home2", ""));
        address2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(CheckoutActivity.this, SettingsAddress2.class);
                startActivity(add);
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
}
