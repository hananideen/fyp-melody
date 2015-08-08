package com.fyp.melody;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
                }
            }
        });

    }


//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        getFragmentManager().beginTransaction().replace(android.R.id.content,
//                new MainSettingsFragment()).commit();
//    }
//
//    public static class MainSettingsFragment extends PreferenceFragment {
//
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            addPreferencesFromResource(R.xml.preference);
//
//        }
//    }

}
