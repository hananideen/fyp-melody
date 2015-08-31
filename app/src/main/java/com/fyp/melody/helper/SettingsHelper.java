package com.fyp.melody.helper;

import android.content.res.Resources;

import com.fyp.melody.model.Settings;

import java.util.List;
import java.util.Vector;

/**
 * Created by Hananideen on 2/6/2015.
 */
public class SettingsHelper {

    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static List<Settings> catalog;

    public static List<Settings> getCatalog(Resources res){
        if(catalog == null) {
            catalog = new Vector<Settings>();
            catalog.add(new Settings("User Profile", "Change username and profile picture"));
            catalog.add(new Settings("Password", "Change password"));
            catalog.add(new Settings("Address", "Add or change address"));
            catalog.add(new Settings("Feedback", "Send us feedback"));
        }

        return catalog;
    }
}
