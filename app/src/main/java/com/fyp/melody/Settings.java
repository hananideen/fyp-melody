package com.fyp.melody;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Hananideen on 31/5/2015.
 */
public class Settings extends AppCompatActivity {

    private List<SettingsList> mProductList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setings);

        mProductList = SettingsHelper.getCatalog(getResources());


        ListView listViewCatalog = (ListView) findViewById(R.id.ListViewSettings);
        listViewCatalog.setAdapter(new SettingsListAdapter(mProductList, getLayoutInflater()));

        listViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        Intent profile = new Intent(Settings.this, SettingsProfile.class);
                        startActivity(profile);
                        break;
                    case 1:
                        Intent pass = new Intent(Settings.this, SettingsPassword.class);
                        startActivity(pass);
                        break;
                    case 2:
                        Intent address = new Intent(Settings.this, SettingsAddress.class);
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
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback from Melody app");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Feedback on this app:");

        try {
            startActivity(Intent.createChooser(emailIntent, "Choose an Email client"));
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
