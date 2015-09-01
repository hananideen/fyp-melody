package com.fyp.melody.JSON;

import org.json.JSONObject;

/**
 * Created by Hananideen on 1/7/2015.
 */
public class Json2Menu {

    public JSONObject jsonObject;

    public String menuName, menuDescription, menuPrice, menuImage;
    public int menuID;

    public Json2Menu(){}


    public Json2Menu (JSONObject json){
        jsonObject = json;
        if (json!=null)
        {
            menuName= json.optString("name");
            menuPrice = json.optString("price");
            menuDescription = json.optString("description");
            menuID = json.optInt("id");
            menuImage = json.optString("logo");
        }
        else{
            menuName = "";
            menuDescription = "";
            menuPrice = "";
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
