package com.fyp.melody.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.fyp.melody.ApplicationLoader;
import com.fyp.melody.JSON.Json2Menu;
import com.fyp.melody.R;
import com.fyp.melody.VolleySingleton;
import com.fyp.melody.adapter.MenuAdapter;
import com.fyp.melody.helper.ShoppingCartHelper;
import com.fyp.melody.model.Menus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hananideen on 1/9/2015.
 */
public class MenuDetailsActivity extends AppCompatActivity {

    int id, position;
    String name, description,image;
    double price;
    MenuAdapter menuListAdapter;
    List<Menus> MenuList;
    private ListView MenuListView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_details);

        MenuList = new ArrayList<Menus>();
        menuListAdapter = new MenuAdapter(MenuList, getLayoutInflater(), false);
        MenuListView = (ListView) findViewById(R.id.listViewHidden);
        MenuListView.setAdapter(menuListAdapter);

        Intent menu = getIntent();
        position = menu.getIntExtra("position", 0);
        id =  menu.getIntExtra("id", 0);
        name = menu.getStringExtra("name");
        description = menu.getStringExtra("description");
        price = menu.getDoubleExtra("price", 0);
        image = menu.getStringExtra("image");

        final Menus[] selectedMenu = new Menus[1];

        NetworkImageView productImageView = (NetworkImageView) findViewById(R.id.ImageViewProduct);
        TextView productTitleTextView = (TextView) findViewById(R.id.TextViewProductTitle);
        TextView productDetailsTextView = (TextView) findViewById(R.id.TextViewProductDetails);
        TextView productPriceTextView = (TextView) findViewById(R.id.TextViewProductPrice);
        TextView textViewCurrentQuantity = (TextView) findViewById(R.id.textViewCurrentlyInCart);

        productImageView.setImageUrl("http://mynetsys.com/restaurant/" + image, VolleySingleton.getInstance().getImageLoader());
        productTitleTextView.setText(name);
        productDetailsTextView.setText(description);
        productPriceTextView.setText("RM" + String.format("%.2f", price));

        JsonArrayRequest menuRequest = new JsonArrayRequest(ApplicationLoader.getIp("restaurant/menu.php"), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Menus menu = new Menus(new Json2Menu(obj));
                        MenuList.add(0, menu);
                        selectedMenu[0] = MenuList.get(position);
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
            }
        });

        VolleySingleton.getInstance().getRequestQueue().add(menuRequest);

        textViewCurrentQuantity.setText("Currently in Cart: "+ ShoppingCartHelper.getProductQuantity(selectedMenu[0]));

        final EditText editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);

        Button addToCartButton = (Button) findViewById(R.id.ButtonAddToCart);
        addToCartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int quantity = 0;
                try {
                    quantity = Integer.parseInt(editTextQuantity.getText().toString());
                    if (quantity < 0) {
                        Toast.makeText(getBaseContext(), "Please enter a quantity of 0 or higher", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Please enter a numeric quantity", Toast.LENGTH_SHORT).show();
                    return;
                }
                ShoppingCartHelper.setQuantity(selectedMenu[0], quantity);
                finish();
            }
        });
    }

}

