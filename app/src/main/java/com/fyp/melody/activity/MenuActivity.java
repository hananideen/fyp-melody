package com.fyp.melody.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fyp.melody.ApplicationLoader;
import com.fyp.melody.JSON.Json2Menu;
import com.fyp.melody.JSON.Json2Restaurants;
import com.fyp.melody.R;
import com.fyp.melody.VolleySingleton;
import com.fyp.melody.adapter.MenuAdapter;
import com.fyp.melody.model.Menus;
import com.fyp.melody.model.Restaurants;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Hananideen on 1/7/2015.
 */
public class MenuActivity extends ActionBarActivity {

    public static final String Settings_PREFS_NAME = "SettingsFile";

    List<NameValuePair> params;
    private ListView MenuListView;
    SharedPreferences Settings;
    List<Menus> MenuList;
    MenuAdapter menuListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Settings = getSharedPreferences(Settings_PREFS_NAME, 0);

        MenuList = new ArrayList<Menus>();
        menuListAdapter = new MenuAdapter(this,MenuList);
        MenuListView = (ListView) findViewById(R.id.MenulistView);
        MenuListView.setAdapter(menuListAdapter);

//        Intent menu = getIntent();
//        int id =  menu.getIntExtra("id", 0);
//        final Restaurants restaurants = new Restaurants();
//        restaurants.setRestaurantID(id);

        MenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Menus menus = menuListAdapter.getMenus(position);
                Log.v("", String.valueOf(menus.getMenuID()));
                Intent menuDetails = new Intent(MenuActivity.this, MenuDetailsActivity.class);
                menuDetails.putExtra("position", position);
                menuDetails.putExtra("id", menus.getMenuID());
                menuDetails.putExtra("name", menus.getMenuName());
                menuDetails.putExtra("price", menus.getMenuPrice());
                menuDetails.putExtra("description", menus.getMenuDescription());
                menuDetails.putExtra("image", menus.getMenuImage());
                startActivity(menuDetails);
            }
        });

        Button viewShoppingCart = (Button) findViewById(R.id.ButtonViewCart);
        viewShoppingCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent viewShoppingCartIntent = new Intent(getBaseContext(), ShoppingCartActivity.class);
                startActivity(viewShoppingCartIntent);
            }
        });

        JsonArrayRequest menuRequest = new JsonArrayRequest(ApplicationLoader.getIp("restaurant/"), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Menus menu = new Menus(new Json2Menu(obj));
                        MenuList.add(0, menu);
                        menuListAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VolleyServer", "Error: " + error.getMessage());
                Toast.makeText(getApplication(), "Cannot connect to server", Toast.LENGTH_SHORT).show();
                LoadData();
                menuListAdapter.notifyDataSetChanged();
            }
        });

        VolleySingleton.getInstance().getRequestQueue().add(menuRequest);

    }

    protected void onResume() {
        super.onResume();

        Log.d("Resume","app is resuming");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("destroy","app is destroyed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("pause", "app is pausing");
    }


    HashMap<String, String> getparams(int restID){

        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(restID));

        return params;
    }

    public void LoadData (){

        for(int i =0;i<5;i++)
        {
            Menus menus = new Menus();
            menus.setMenuName("Menu Name " + i);
            menus.setMenuPrice("Price " + i);
            MenuList.add(0, menus);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_restaurant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent setting = new Intent(MenuActivity.this, SettingsActivity.class);
            startActivity(setting);
            return true;
        }

        if (id == R.id.action_cart) {
            Intent cart = new Intent(MenuActivity.this, ShoppingCartActivity.class);
            startActivity(cart);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
