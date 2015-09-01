package com.fyp.melody.helper;

import android.view.Menu;

import com.fyp.melody.model.Menus;
import com.fyp.melody.model.ShoppingCart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Hananideen on 1/9/2015.
 */
public class ShoppingCartHelper {
    private static Map<Menus, ShoppingCart> cartMap = new HashMap<Menus, ShoppingCart>();

    public static void setQuantity(Menus menus, int quantity) {
        ShoppingCart curEntry = cartMap.get(menus);
        if(quantity <= 0) {
            if(curEntry != null)
                removeProduct(menus);
            return;
        }

        if(curEntry == null) {
            curEntry = new ShoppingCart(menus, quantity);
            cartMap.put(menus, curEntry);
            return;
        }
        curEntry.setQuantity(quantity);
    }

    public static int getProductQuantity(Menus menus) {
        ShoppingCart curEntry = cartMap.get(menus);
        if(curEntry != null)
            return curEntry.getQuantity();
        return 0;
    }

    public static void removeProduct(Menus menus) {
        cartMap.remove(menus);
    }

    public static List<Menus> getCartList() {
        List<Menus> cartList = new Vector<Menus>(cartMap.keySet().size());
        for(Menus p : cartMap.keySet()) {
            cartList.add(p);
        }
        return cartList;
    }
}
