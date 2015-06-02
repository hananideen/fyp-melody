package com.fyp.melody;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Hananideen on 31/5/2015.
 */
public class Settings extends Fragment {

    private List<SettingsList> mProductList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_setings, container, false);

        mProductList = SettingsHelper.getCatalog(getResources());


        ListView listViewCatalog = (ListView) rootView.findViewById(R.id.ListViewSettings);
        listViewCatalog.setAdapter(new SettingsListAdapter(mProductList, getActivity().getLayoutInflater()));

        listViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        Intent profile = new Intent(getActivity(), SettingsProfile.class);
                        startActivity(profile);
                        break;
                    case 1:
                        Intent pass = new Intent(getActivity(), SettingsProfile.class);
                        startActivity(pass);
                        break;
                    case 2:
                        Intent address = new Intent(getActivity(), SettingsProfile.class);
                        startActivity(address);
                        break;
                }
            }
        });

        return rootView;

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
