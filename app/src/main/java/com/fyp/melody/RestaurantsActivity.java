package com.fyp.melody;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
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
        setContentView(R.layout.activity_restaurants);

        RestaurantsList = new ArrayList<Restaurants>();
        restListAdapter = new RestaurantsAdapter(this, RestaurantsList);
        RestListView = (ListView) findViewById(R.id.RestListView);
        RestListView.setAdapter(restListAdapter);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApplicationLoader.getIp("restaurant.json"),new JSONObject(getparams()), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Boolean success = response.optBoolean("success");
                JSONArray jsonArray;
                Log.e("jsonobjectRequest", "got response");
                if (success){
                    Log.e("jsonObjectRequest", "Success");
                    jsonArray = response.optJSONArray("data");
                    for (int i = 0; i <jsonArray.length();i++){
                        Restaurants restaurants = new Restaurants(new Json2Restaurants(jsonArray.optJSONObject(i)));
                        RestaurantsList.add(0, restaurants);
                        Log.d("json item", jsonArray.optJSONObject(i).toString());
                        Log.e("jsonobjectrequest", restaurants.toString());
                    }
                    restListAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VolleyServer", "Error" + error.getMessage());
                Toast.makeText(getApplication(), "No internet connection!", Toast.LENGTH_SHORT).show();
                LoadData();
                restListAdapter.notifyDataSetChanged();
            }
        });

        VolleySingleton.getInstance().getRequestQueue().add(jsonObjectRequest);
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
        params.put("phoneNumber", "0148204633");

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
            Intent setting = new Intent(RestaurantsActivity.this, Settings.class);
            startActivity(setting);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
