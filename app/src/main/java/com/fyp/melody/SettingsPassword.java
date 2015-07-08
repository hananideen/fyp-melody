package com.fyp.melody;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Hananideen on 8/7/2015.
 */
public class SettingsPassword extends Activity {

    private EditText editPass, editPass2;
    private Button buttonSave;

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_password);

        settings = getSharedPreferences(ApplicationLoader.Settings_PREFS_NAME, 0);
        editor = settings.edit();

        editPass = (EditText) findViewById(R.id.editPass);
        editPass.setText(settings.getString("Password", ""));

        editPass2 = (EditText) findViewById(R.id.editPass2);
        editPass2.setText(settings.getString("confirmPassword", ""));

        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (editPass != editPass2 ) {
//                    Toast.makeText(getApplicationContext(), "The passwords are different.", Toast.LENGTH_LONG).show();
//                } else {
                    editor.putString("Password", editPass.getText().toString());
                    editor.putString("confirmPassword", editPass2.getText().toString());
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Password saved.", Toast.LENGTH_LONG).show();
                    finish();
//                }
            }
        });
    }
}
