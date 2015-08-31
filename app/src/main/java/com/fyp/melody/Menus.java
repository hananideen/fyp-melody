package com.fyp.melody;

/**
 * Created by Hananideen on 1/7/2015.
 */
public class Menus {

    public String MenuName;
    public String MenuPrice;
    private String MenuImage;
    private int MenuID;

    public Menus(){
    }

    public Menus(String menuName, String menuPrice, String menuImage, int menuID) {
        MenuName = menuName;
        MenuPrice = menuPrice;
        MenuImage = menuImage;
        MenuID = menuID;
    }

    public Menus(Json2Menu jMenu){
        MenuName = jMenu.menuName;
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
