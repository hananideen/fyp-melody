package com.fyp.melody.JSON;

import org.json.JSONObject;

/**
 * Created by Hananideen on 2/9/2015.
 */
public class Json2Trackings {

    public JSONObject jsonObject;
    public String status;
    public int id;

    public Json2Trackings(){}

    public Json2Trackings(JSONObject json){
        jsonObject = json;
        if (json!=null)
        {
            status = json.optString("Status");
            id = json.optInt("Order_id");

        }
        else{
            status = "";
            id = 0;
        }
    }
}
