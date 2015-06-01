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

        View rootView = inflater.inflate(R.layout.restaurant_list, container, false);

        mProductList = RestaurantHelper.getCatalog(getResources());


        ListView listViewCatalog = (ListView) rootView.findViewById(R.id.ListViewCatalog);
        listViewCatalog.setAdapter(new RestaurantListAdapter(mProductList, getActivity().getLayoutInflater(), false));

        listViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent productDetailsIntent = new Intent(getActivity(),SettingsProfile.class);
                productDetailsIntent.putExtra(RestaurantHelper.PRODUCT_INDEX, position);
                startActivity(productDetailsIntent);
            }
        });

        return rootView;

    }

}
