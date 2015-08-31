package com.fyp.melody.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.fyp.melody.R;
import com.fyp.melody.model.Restaurants;

import java.util.List;

/**
 * Created by Hananideen on 31/8/2015.
 */
public class RestaurantsAdapter extends BaseAdapter {

    private Activity mactivity;
    private List<Restaurants> RestaurantsList;
    private LayoutInflater inflater;
    private Restaurants curRest;

    public RestaurantsAdapter (Activity mactivity, List<Restaurants> RestaurantsList){
        this.mactivity = mactivity;
        this.RestaurantsList = RestaurantsList;
    }

    @Override
    public int getCount() {
        return RestaurantsList.size();
    }

    @Override
    public Object getItem(int position) {
        return RestaurantsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {

            inflater = (LayoutInflater) mactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = inflater.inflate(R.layout.restaurant_items, parent, false);

        }

        TextView restName = (TextView) convertView.findViewById(R.id.RestName);
        TextView restType = (TextView) convertView.findViewById(R.id.RestType);
        NetworkImageView restImg = (NetworkImageView) convertView.findViewById(R.id.RestImage);

        curRest = RestaurantsList.get(position);

        restName.setText(curRest.getRestaurantName());
        restType.setText(curRest.getRestaurantType());

        return convertView;
    }

    public Restaurants getRestaurants (int position) {
        return RestaurantsList.get(position);
    }
}
