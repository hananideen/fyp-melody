package com.fyp.melody;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
 * Created by Hananideen on 1/7/2015.
 */
public class MenuActivity extends ActionBarActivity {

    public static final String Settings_PREFS_NAME = "SettingsFile";

    List<NameValuePair> params;
//    ServerRequest sr;
//    PhoneStatus cs;
    private ListView MenuListView;
    SharedPreferences Settings;

    List<Menus> MenuList;

    MenuAdapter menuListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Settings = getSharedPreferences(Settings_PREFS_NAME, 0);

//        sr = new ServerRequest();
//        cs = new PhoneStatus();

        MenuList = new ArrayList<Menus>();

        menuListAdapter = new MenuAdapter(this,MenuList);

        initUI();

        MenuListView.setAdapter(menuListAdapter);

//        PromotionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Promo promo = promoListAdapter.getPromo(position);
//                Intent promoDetailsIntent = new Intent(getBaseContext(), PromotionDetails.class);
//                promoDetailsIntent.putExtra("CompName", promo.CompanyName);
//                promoDetailsIntent.putExtra("Description", promo.Description);
//                promoDetailsIntent.putExtra("DueDate", promo.DueDate);
//                promoDetailsIntent.putExtra("Price", promo.PromoPrice);
//                promoDetailsIntent.putExtra("position", position);
//
//                startActivity(promoDetailsIntent);
//                overridePendingTransition(R.animator.flip_out, R.animator.hold);
//            }
//        });

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
                        Menus menus = new Menus(new Json2Menu(jsonArray.optJSONObject(i)));
                        MenuList.add(0, menus);
                        Log.d("json item", jsonArray.optJSONObject(i).toString());
                        Log.e("jsonobjectrequest", menus.toString());
                    }
                    menuListAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VolleyServer", "Error" + error.getMessage());
                Toast.makeText(getApplication(), "No internet connection!", Toast.LENGTH_SHORT).show();
                LoadData();
                menuListAdapter.notifyDataSetChanged();
            }
        });

        VolleySingleton.getInstance().getRequestQueue().add(jsonObjectRequest);

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
        Log.d("pause","app is pausing");
    }

    private void initUI() {
        MenuListView = (ListView) findViewById(R.id.MenulistView);

    }

    HashMap<String, String> getparams(){

        HashMap<String, String> params = new HashMap<>();
        params.put("phoneNumber", "0148204633");

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
            Intent setting = new Intent(MenuActivity.this, Settings.class);
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
