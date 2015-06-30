package com.fyp.melody;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hananideen on 2/6/2015.
 */
public class ShoppingCartActivity extends ActionBarActivity implements View.OnClickListener {

    private List<Product> mCartList;
    private ProductAdapter mProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);


        mCartList = ShoppingCartHelper.getCartList();

        // Make sure to clear the selections
        for(int i=0; i<mCartList.size(); i++) {
            mCartList.get(i).selected = false;
        }


        // Create the list
        final ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        mProductAdapter = new ProductAdapter(mCartList, getLayoutInflater(), true);
        listViewCatalog.setAdapter(mProductAdapter);

        listViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent productDetailsIntent = new Intent(getBaseContext(),ProductDetails.class);
                productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX, position);
                startActivity(productDetailsIntent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Refresh the data
        if(mProductAdapter != null) {
            mProductAdapter.notifyDataSetChanged();
        }

        double subTotal = 0;
        for(Product p : mCartList) {
            int quantity = ShoppingCartHelper.getProductQuantity(p);
            subTotal += p.price * quantity;
        }

        TextView productPriceTextView = (TextView) findViewById(R.id.TextViewSubtotal);
        productPriceTextView.setText("Subtotal: RM" + subTotal);
    }

    public void checkout (View v) {
        TextView productPriceTextView = (TextView) findViewById(R.id.TextViewSubtotal);
        String subTotal = null;
        productPriceTextView.setText("Subtotal: RM" + subTotal);
        Intent payment = new Intent(getBaseContext(), TrackingActivity.class);
        payment.putExtra("subtotal", subTotal);
        startActivity(payment);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }
}
