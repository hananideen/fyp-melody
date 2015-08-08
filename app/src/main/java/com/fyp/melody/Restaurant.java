package com.fyp.melody;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Hananideen on 31/5/2015.
 */
public class Restaurant extends AppCompatActivity {

    private List<RestaurantList> mProductList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Boolean Login = ApplicationLoader.getInstance().getSettingPrefFile().getBoolean("hasLoggedIn", false);

        if (!Login) {
            Log.e("MainActivity", "login");
            Intent intent = new Intent(this, LoginPhone.class);
            startActivity(intent);
            finish();

        } else {
            Log.e("MainActivity", "main");
            setContentView(R.layout.fragment_restaurant);
        }

        mProductList = RestaurantHelper.getCatalog(getResources());


        ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        listViewCatalog.setAdapter(new RestaurantListAdapter(mProductList, getLayoutInflater(), false));

        listViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        Intent tokio = new Intent(Restaurant.this, Restaurant1.class);
                        startActivity(tokio);
                        break;
                    case 1:
                        Intent sana = new Intent(Restaurant.this, Restaurant1.class);
                        startActivity(sana);
                        break;
                    case 2:
                        Intent sedap = new Intent(Restaurant.this, Restaurant1.class);
                        startActivity(sedap);
                        break;
                    case 3:
                        Intent tappers = new Intent(Restaurant.this, Restaurant1.class);
                        startActivity(tappers);
                        break;
                    case 4:
                        Intent salam = new Intent(Restaurant.this, Restaurant1.class);
                        startActivity(salam);
                        break;
                    case 5:
                        Intent deli = new Intent(Restaurant.this, MenuActivity.class);
                        startActivity(deli);
                        break;
                }

//                Intent productDetailsIntent = new Intent(getActivity(),SettingsProfile.class);
//                productDetailsIntent.putExtra(RestaurantHelper.PRODUCT_INDEX, position);
//                startActivity(productDetailsIntent);
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent setting = new Intent(Restaurant.this, Settings.class);
            startActivity(setting);
            return true;
        }

        if (id == R.id.action_track) {
            Intent track = new Intent(Restaurant.this, Tracking.class);
            startActivity(track);
            return true;
        }

        if (id == R.id.action_cart) {
            Intent cart = new Intent(Restaurant.this, ShoppingCartActivity.class);
            startActivity(cart);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
