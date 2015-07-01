package com.fyp.melody;

import org.json.JSONObject;

/**
 * Created by Hananideen on 1/7/2015.
 */
public class Json2Menu {

    public JSONObject jsonObject, temp;

    public String menuName, menuPrice, companyImage;

    public Json2Menu(){}


    public Json2Menu (JSONObject json){
        jsonObject = json;
        if (json!=null)
        {
            temp= json.optJSONArray("Product").optJSONObject(0);
            menuName= temp.optString("prodDescription");
            menuPrice = json.optString("prodPrice");
        }
        else{
            menuName = "";
            menuPrice = "";
        }
    }

    Boolean isNull(){
        if (jsonObject == null)
            return true;
        else
            return false;
    }

}
