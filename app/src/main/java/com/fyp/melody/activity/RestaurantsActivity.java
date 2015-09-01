package com.fyp.melody.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fyp.melody.ApplicationLoader;
import com.fyp.melody.JSON.Json2Restaurants;
import com.fyp.melody.R;
import com.fyp.melody.Restaurant;
import com.fyp.melody.model.Restaurants;
import com.fyp.melody.VolleySingleton;
import com.fyp.melody.adapter.RestaurantsAdapter;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Hananideen on 31/8/2015.
 */
public class RestaurantsActivity extends AppCompatActivity {

    List<NameValuePair> params;
    private ListView RestListView;
    List<Restaurants> RestaurantsList;
    RestaurantsAdapter restListAdapter;

    protected void onCreate (Bundle savedInstance) {
        super.onCreate(savedInstance);

        Boolean Login = ApplicationLoader.getInstance().getSettingPrefFile().getBoolean("hasLoggedIn", false);

        if (!Login) {
            Log.e("MainActivity", "login");
            Intent intent = new Intent(this, LoginPhone.class);
            startActivity(intent);
            finish();

        } else {
            Log.e("MainActivity", "main");
            setContentView(R.layout.activity_restaurants);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.melody_logo);
        }

        RestaurantsList = new ArrayList<Restaurants>();
        restListAdapter = new RestaurantsAdapter(this, RestaurantsList);
        RestListView = (ListView) findViewById(R.id.RestListView);
        RestListView.setAdapter(restListAdapter);

        RestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurants restaurants = restListAdapter.getRestaurants(position);
                Log.v("List view clicked: ", restaurants.getRestaurantName());
                Intent menu = new Intent(RestaurantsActivity.this, MenuActivity.class);
                menu.putExtra("id", restaurants.getRestaurantName());
                startActivity(menu);
            }
        });

        JsonArrayRequest restRequest = new JsonArrayRequest(ApplicationLoader.getIp("restaurant/"), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Restaurants restaurants = new Restaurants(new Json2Restaurants(obj));
                        RestaurantsList.add(0, restaurants);
                        restListAdapter.notifyDataSetChanged();
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
                restListAdapter.notifyDataSetChanged();
            }
        });

        VolleySingleton.getInstance().getRequestQueue().add(restRequest);
    }

    protected void onResume() {
        super.onResume();

        Log.d("Resume", "app is resuming");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("destroy","app is destroyed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("pause","app is pausing");
    }

    HashMap<String, String> getparams(){

        HashMap<String, String> params = new HashMap<>();
        params.put("", "");

        return params;
    }

    public void LoadData (){

        for(int i =0;i<5;i++)
        {
            Restaurants restaurants = new Restaurants();
            restaurants.setRestaurantName("Restaurants Name " + i);
            restaurants.setRestaurantType("Type " + i);
            RestaurantsList.add(0, restaurants);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent setting = new Intent(RestaurantsActivity.this, SettingsActivity.class);
            startActivity(setting);
            return true;
        }

        if (id == R.id.action_inapp){
            Intent inapp = new Intent(RestaurantsActivity.this, Restaurant.class);
            startActivity(inapp);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
