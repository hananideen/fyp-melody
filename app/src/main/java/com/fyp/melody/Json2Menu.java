package com.fyp.melody;

import org.json.JSONObject;

/**
 * Created by Hananideen on 1/7/2015.
 */
public class Json2Menu {

    public JSONObject jsonObject, temp;

    public String  productName, productCaption, productDescription, productImage, productPrice;
    public String companyName, companyReputation;
    public String Latitude, Longitude;
    public String DueDate;
    public int No_of_Likes,No_of_Dislikes;

    public Json2Menu(){}


    public Json2Menu (JSONObject json){
        jsonObject = json;
        if (json!=null)
        {
            companyName = json.optString("CompName");
            companyReputation = json.optString("compReputation");

            temp= json.optJSONArray("Product").optJSONObject(0);
            productName = temp.optString("prodName");
            productCaption = temp.optString("prodCaption");
            productDescription= temp.optString("prodDescription");
            DueDate = temp.optString("prodDueDate");
            Latitude = json.optJSONObject("CompLocation").optString("Lat");
            Longitude = json.optJSONObject("CompLocation").optString("Lon");
            No_of_Likes = temp.optInt("No_of_Likes");
            productImage = temp.optString("ProdImage");
            No_of_Dislikes = temp.optInt("No_of_Dislikes");
            productPrice = json.optString("prodPrice");
        }
        else{
            companyName = "";
            companyReputation = "";
            productName = "";
            productCaption = "";
            productDescription = "";
            productImage="";
            productPrice = "";
            Latitude = "";
            Longitude = "";
            No_of_Likes = 0;
            No_of_Dislikes = 0;

        }
    }

    Boolean isNull(){
        if (jsonObject == null)
            return true;
        else
            return false;
    }

}
