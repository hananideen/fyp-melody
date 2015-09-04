package com.fyp.melody.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fyp.melody.ApplicationLoader;
import com.fyp.melody.R;
import com.fyp.melody.Restaurant;

/**
 * Created by Hananideen on 17/8/2015.
 */
public class LoginPassword extends Activity {

    private EditText editPass, editPass2;
    private Button buttonSave, buttonCancel;
    private ImageButton buttonHelp;
    private String password, confirmPassword;

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    protected void onCreate (final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_password);

        settings = getSharedPreferences(ApplicationLoader.Settings_PREFS_NAME, 0);
        editor = settings.edit();

        editPass = (EditText) findViewById(R.id.editPass);
        editPass.setText(settings.getString("Password", ""));

        editPass2 = (EditText) findViewById(R.id.editPass2);
        editPass2.setText(settings.getString("confirmPassword", ""));

        buttonHelp = (ImageButton) findViewById(R.id.help);
        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "This password will be used during order confirmation", Toast.LENGTH_LONG).show();
            }
        });

        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password = editPass.getText().toString();
                confirmPassword = editPass2.getText().toString();

                if (password.length()==0 && confirmPassword.length()==0) {
                    Toast.makeText(getApplicationContext(), "Please insert your password", Toast.LENGTH_LONG).show();

                } else if (password.equals(confirmPassword)) {
                    editor.putString("Password", editPass.getText().toString());
                    editor.putString("confirmPassword", editPass2.getText().toString());
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Password saved", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginPassword.this, RestaurantsActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Both passwords are different, please insert correctly", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
