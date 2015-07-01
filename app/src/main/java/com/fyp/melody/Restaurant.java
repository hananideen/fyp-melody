package com.fyp.melody;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Hananideen on 31/5/2015.
 */
public class Restaurant extends Fragment {

    private List<RestaurantList> mProductList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_restaurant, container, false);

        mProductList = RestaurantHelper.getCatalog(getResources());


        ListView listViewCatalog = (ListView) rootView.findViewById(R.id.ListViewCatalog);
        listViewCatalog.setAdapter(new RestaurantListAdapter(mProductList, getActivity().getLayoutInflater(), false));

        listViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        Intent tokio = new Intent(getActivity(), Restaurant1.class);
                        startActivity(tokio);
                        break;
                    case 1:
                        Intent sana = new Intent(getActivity(), Restaurant1.class);
                        startActivity(sana);
                        break;
                    case 2:
                        Intent sedap = new Intent(getActivity(), Restaurant1.class);
                        startActivity(sedap);
                        break;
                    case 3:
                        Intent tappers = new Intent(getActivity(), Restaurant1.class);
                        startActivity(tappers);
                        break;
                    case 4:
                        Intent salam = new Intent(getActivity(), Restaurant1.class);
                        startActivity(salam);
                        break;
                    case 5:
                        Intent deli = new Intent(getActivity(), MenuActivity.class);
                        startActivity(deli);
                        break;
                }

//                Intent productDetailsIntent = new Intent(getActivity(),SettingsProfile.class);
//                productDetailsIntent.putExtra(RestaurantHelper.PRODUCT_INDEX, position);
//                startActivity(productDetailsIntent);
            }
        });

        return rootView;

    }

}
