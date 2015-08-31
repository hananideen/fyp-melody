package com.fyp.melody.helper;

import android.content.res.Resources;

import com.fyp.melody.Product;
import com.fyp.melody.R;
import com.fyp.melody.model.ShoppingCartEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Hananideen on 2/6/2015.
 */
public class ShoppingCartHelper {

    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static List<Product> catalog;
    private static Map<Product, ShoppingCartEntry> cartMap = new HashMap<Product, ShoppingCartEntry>();

    public static List<Product> getCatalog(Resources res){
        if(catalog == null) {
            catalog = new Vector<Product>();
            catalog.add(new Product("Chicken Chop", res.getDrawable(R.mipmap.ic_launcher),
                    "Chicken chop in black pepper sauce served with mashed potato, french fries and coleslaw", 9.90));
            catalog.add(new Product("Lamb Chop", res.getDrawable(R.mipmap.ic_launcher),
                    "Lamb chop in black pepper sauce served with mashed potato, french fries and coleslaw", 10.90));
            catalog.add(new Product("Spagetti Bolognese", res.getDrawable(R.mipmap.ic_launcher),
                    "Spagetti bolognese served with chicken tomato spagetti sauce.", 7.00));
            catalog.add(new Product("Hainan Chicken Rice", res.getDrawable(R.mipmap.ic_launcher),
                    "Hainan chicken rice served with chicken in black pepper sauce and soup.", 5.00));
            catalog.add(new Product("French Fries", res.getDrawable(R.mipmap.ic_launcher),
                    "Side dish.", 4.00));
        }

        return catalog;
    }

    public static void setQuantity(Product product, int quantity) {
        // Get the current cart entry
        ShoppingCartEntry curEntry = cartMap.get(product);

        // If the quantity is zero or less, remove the products
        if(quantity <= 0) {
            if(curEntry != null)
                removeProduct(product);
            return;
        }

        // If a current cart entry doesn't exist, create one
        if(curEntry == null) {
            curEntry = new ShoppingCartEntry(product, quantity);
            cartMap.put(product, curEntry);
            return;
        }

        // Update the quantity
        curEntry.setQuantity(quantity);
    }

    public static int getProductQuantity(Product product) {
        // Get the current cart entry
        ShoppingCartEntry curEntry = cartMap.get(product);

        if(curEntry != null)
            return curEntry.getQuantity();

        return 0;
    }

    public static void removeProduct(Product product) {
        cartMap.remove(product);
    }

    public static List<Product> getCartList() {
        List<Product> cartList = new Vector<Product>(cartMap.keySet().size());
        for(Product p : cartMap.keySet()) {
            cartList.add(p);
        }

        return cartList;
    }
}
