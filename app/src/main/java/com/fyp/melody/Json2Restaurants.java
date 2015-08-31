package com.fyp.melody;

import org.json.JSONObject;

/**
 * Created by Hananideen on 31/8/2015.
 */
public class Json2Restaurants {

    public JSONObject jsonObject, temp;
    public String restaurantName, restaurantType, restaurantImage;
    public int restaurantID;

    public Json2Restaurants(){}

    public Json2Restaurants(JSONObject json){
        jsonObject = json;
        if (json!=null)
        {
            temp= json.optJSONArray("Product").optJSONObject(0);
            restaurantName= temp.optString("prodDescription");
            restaurantType = json.optString("prodPrice");
            //TODO get id and image
        }
        else{
            restaurantName = "";
            restaurantType = "";
            restaurantImage = "";
            restaurantID = 0;
        }
    }
}
