package com.fyp.melody;

import android.content.res.Resources;

import java.util.List;
import java.util.Vector;

/**
 * Created by Hananideen on 2/6/2015.
 */
public class RestaurantHelper {

    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static List<RestaurantList> catalog;

    public static List<RestaurantList> getCatalog(Resources res){
        if(catalog == null) {
            catalog = new Vector<RestaurantList>();
            catalog.add(new RestaurantList("Tokio Cafe", res.getDrawable(R.mipmap.ic_launcher), "Western & Japanese Cuisine"));
            catalog.add(new RestaurantList("Sana'a", res.getDrawable(R.mipmap.ic_launcher), "Arabic Cuisine"));
            catalog.add(new RestaurantList("Dsedap Sengoti", res.getDrawable(R.mipmap.ic_launcher), "Malay Cuisine"));
            catalog.add(new RestaurantList("Tappers", res.getDrawable(R.mipmap.ic_launcher), "Western"));
            catalog.add(new RestaurantList("Salam Noodles", res.getDrawable(R.mipmap.ic_launcher), "Chinese Muslim Cuisine"));
            catalog.add(new RestaurantList("DeliDelphia", res.getDrawable(R.mipmap.ic_launcher), "Western"));
        }

        return catalog;
    }
}
