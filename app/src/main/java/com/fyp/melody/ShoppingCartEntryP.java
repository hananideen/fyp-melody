package com.fyp.melody;

import com.fyp.melody.Product;

/**
 * Created by Hananideen on 2/6/2015.
 */
public class ShoppingCartEntryP {

    private Product mProduct;
    private int mQuantity;

    public ShoppingCartEntryP(Product product, int quantity) {
        mProduct = product;
        mQuantity = quantity;
    }

    public Product getProduct() {
        return mProduct;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

}