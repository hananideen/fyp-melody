package com.fyp.melody;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Hananideen on 2/6/2015.
 */
public class Restaurant1 extends ActionBarActivity{

    private List<Product> mProductList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalog);


        mProductList = ShoppingCartHelper.getCatalog(getResources());


        ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        listViewCatalog.setAdapter(new ProductAdapter(mProductList, getLayoutInflater(), false));

        listViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent productDetailsIntent = new Intent(getBaseContext(),ProductDetails.class);
                productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX, position);
                startActivity(productDetailsIntent);
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

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_restaurant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent setting = new Intent(Restaurant1.this, Settings.class);
            startActivity(setting);
            return true;
        }

        if (id == R.id.action_cart) {
            Intent cart = new Intent(Restaurant1.this, ShoppingCartActivity.class);
            startActivity(cart);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
