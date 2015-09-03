package com.fyp.melody.model;

import com.fyp.melody.JSON.Json2Menu;

/**
 * Created by Hananideen on 1/7/2015.
 */
public class Menus {

    public String MenuName;
    public String MenuDescription;
    public String MenuPrice;
    private String MenuImage;
    private int MenuID;
    public boolean selected;

    public Menus(){
    }

    public Menus(String menuName, String menuDescription, String menuPrice, String menuImage, int menuID) {
        MenuName = menuName;
        MenuDescription = menuDescription;
        MenuPrice = menuPrice;
        MenuImage = menuImage;
        MenuID = menuID;
    }

    public Menus(Json2Menu jMenu){
        MenuName = jMenu.menuName;
        MenuDescription = jMenu.menuDescription;
        MenuPrice = jMenu.menuPrice;
        MenuImage = jMenu.menuImage;
        MenuID = jMenu.menuID;
    }


    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName (String nameMenu) {
        MenuName = nameMenu;
    }

    public String getMenuDescription () {
        return MenuDescription;
    }

    public void setMenuDescription (String descriptionMenu) {
        MenuDescription = descriptionMenu;
    }

    public String getMenuPrice() {
        return MenuPrice;
    }

    public void setMenuPrice (String priceMenu) {
        MenuPrice = priceMenu;
    }

    public String getMenuImage() {
        return MenuImage;
    }

    public void setMenuImage(String imageMenu) {
        MenuImage = imageMenu;
    }

    public int getMenuID () {
        return MenuID;
    }

    public void setMenuID (int idMenu) {
        MenuID = idMenu;
    }

}
