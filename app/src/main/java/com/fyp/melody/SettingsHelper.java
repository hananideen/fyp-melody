package com.fyp.melody;

import android.content.res.Resources;

import java.util.List;
import java.util.Vector;

/**
 * Created by Hananideen on 2/6/2015.
 */
public class SettingsHelper {

    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static List<SettingsList> catalog;

    public static List<SettingsList> getCatalog(Resources res){
        if(catalog == null) {
            catalog = new Vector<SettingsList>();
            catalog.add(new SettingsList("User Profile", "Change username and profile picture"));
            catalog.add(new SettingsList("Password", "Change password"));
            catalog.add(new SettingsList("Address", "Add or change address"));
        }

        return catalog;
    }
}
