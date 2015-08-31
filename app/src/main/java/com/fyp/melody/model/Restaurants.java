package com.fyp.melody.model;

import com.fyp.melody.JSON.Json2Restaurants;

/**
 * Created by Hananideen on 31/8/2015.
 */
public class Restaurants {

    public String RestaurantName;
    public String RestaurantType;
    public String RestaurantImage;
    public int RestaurantID;

    public Restaurants () {}

    public Restaurants (String restaurantName, String restaurantType, String restaurantImage, int restaurantID){
        RestaurantName = restaurantName;
        RestaurantType = restaurantType;
        RestaurantImage = restaurantImage;
        RestaurantID = restaurantID;
    }

    public Restaurants (Json2Restaurants jRest) {
        RestaurantName = jRest.restaurantName;
        RestaurantType = jRest.restaurantType;
        RestaurantImage = jRest.restaurantImage;
        RestaurantID = jRest.restaurantID;
    }

    public String getRestaurantName () {
        return RestaurantName;
    }

    public void setRestaurantName (String nameRestaurant) {
        RestaurantName = nameRestaurant;
    }

    public String getRestaurantType () {
        return RestaurantType;
    }

    public void setRestaurantType (String typeRestaurant) {
        RestaurantType = typeRestaurant;
    }

    public String getRestaurantImage () {
        return RestaurantImage;
    }

    public void setRestaurantImage (String imageRestaurant) {
        RestaurantImage = imageRestaurant;
    }

    public int getRestaurantID () {
        return RestaurantID;
    }

    public void setRestaurantID (int idRestaurant) {
        RestaurantID = idRestaurant;
    }

}
