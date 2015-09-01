package com.fyp.melody.JSON;

import org.json.JSONObject;

/**
 * Created by Hananideen on 31/8/2015.
 */
public class Json2Restaurants {

    public JSONObject jsonObject;
    public String restaurantName, restaurantType, restaurantImage;
    public int restaurantID;

    public Json2Restaurants(){}

    public Json2Restaurants(JSONObject json){
        jsonObject = json;
        if (json!=null)
        {
            restaurantName= json.optString("Name");
            restaurantType = json.optString("Category");
            restaurantID = json.optInt("id");
            restaurantImage = json.optString("Logo");
        }
        else{
            restaurantName = "";
            restaurantType = "";
            restaurantImage = "";
            restaurantID = 0;
        }
    }
}
