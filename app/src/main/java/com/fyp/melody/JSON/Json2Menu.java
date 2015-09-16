package com.fyp.melody.JSON;

import org.json.JSONObject;

/**
 * Created by Hananideen on 1/7/2015.
 */
public class Json2Menu {

    public JSONObject jsonObject;

    public String menuName, menuDescription, menuImage;
    public double menuPrice;
    public int menuID;

    public Json2Menu(){}


    public Json2Menu (JSONObject json){
        jsonObject = json;
        if (json!=null)
        {
            menuName= json.optString("Name");
            menuPrice = json.optDouble("Price");
            menuDescription = json.optString("Description");
            menuID = json.optInt("Id");
            menuImage = json.optString("Logo");
        }
        else{
            menuName = "";
            menuDescription = "";
            menuPrice = 0;
            menuImage = "";
            menuID = 0;
        }
    }

    Boolean isNull(){
        if (jsonObject == null)
            return true;
        else
            return false;
    }

}
