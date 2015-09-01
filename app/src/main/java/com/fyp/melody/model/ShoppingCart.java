package com.fyp.melody.model;

/**
 * Created by Hananideen on 1/9/2015.
 */
public class ShoppingCart {

    private Menus mMenus;
    private int mQuantity;

    public ShoppingCart(Menus menus, int quantity) {
        mMenus = menus;
        mQuantity = quantity;
    }

    public Menus getMenus() {
        return mMenus;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

}