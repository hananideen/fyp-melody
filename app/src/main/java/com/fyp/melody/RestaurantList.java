package com.fyp.melody;

import android.graphics.drawable.Drawable;

/**
 * Created by Hananideen on 2/6/2015.
 */
public class RestaurantList {

    public String title;
    public Drawable productImage;
    public String description;

    public RestaurantList (String title, Drawable productImage, String description) {
        this.title = title;
        this.productImage = productImage;
        this.description = description;
    }

}
