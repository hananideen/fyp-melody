package com.fyp.melody.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fyp.melody.R;
import com.fyp.melody.model.Settings;
import com.fyp.melody.helper.SettingsHelper;
import com.fyp.melody.adapter.SettingsListAdapter;

import java.util.List;

/**
 * Created by Hananideen on 31/5/2015.
 */
public class SettingsActivity extends AppCompatActivity {

    private List<Settings> mProductList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mProductList = SettingsHelper.getCatalog(getResources());


        ListView listViewCatalog = (ListView) findViewById(R.id.ListViewSettings);
        listViewCatalog.setAdapter(new SettingsListAdapter(mProductList, getLayoutInflater()));

        listViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        Intent profile = new Intent(SettingsActivity.this, SettingsProfile.class);
                        startActivity(profile);
                        break;
                    case 1:
                        Intent pass = new Intent(SettingsActivity.this, SettingsPassword.class);
                        startActivity(pass);
                        break;
                    case 2:
                        Intent address = new Intent(SettingsActivity.this, SettingsAddress.class);
                        startActivity(address);
                        break;
                    case 3:
                        sendEmail();
                        break;
                }
            }
        });

    }

    protected void sendEmail() {

        String[] TO = {"hananee92@gmail.com"};

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback from Meal-o-dy app");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Feedback on this app:");

        try {
            startActivity(Intent.createChooser(emailIntent, "Choose an Email client"));
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
