package com.fyp.melody.JSON;

import org.json.JSONObject;

/**
 * Created by Hananideen on 13/9/2015.
 */
public class Json2Deliveryman {

    public JSONObject jsonObject;
    public String name, phone, plate;

    public Json2Deliveryman() {}

    public Json2Deliveryman(JSONObject json){
        jsonObject = json;
        if (json!=null)
        {
            name = json.optString("Name");
            phone = json.optString("PhoneNumber");
            plate = json.optString("PlateNumber");

        }
        else{
            name = "";
            phone = "";
            plate = "";
        }
    }
}
