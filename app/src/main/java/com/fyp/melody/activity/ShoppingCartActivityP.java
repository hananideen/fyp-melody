package com.fyp.melody.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fyp.melody.Product;
import com.fyp.melody.ProductAdapter;
import com.fyp.melody.ProductDetailsActivity;
import com.fyp.melody.R;
import com.fyp.melody.ShoppingCartHelperP;

import java.util.List;

/**
 * Created by Hananideen on 2/6/2015.
 */
public class ShoppingCartActivityP extends ActionBarActivity implements View.OnClickListener {

    private List<Product> mCartList;
    private ProductAdapter mProductAdapter;
    String total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        mCartList = ShoppingCartHelperP.getCartList();

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
                Intent productDetailsIntent = new Intent(getBaseContext(), ProductDetailsActivity.class);
                productDetailsIntent.putExtra(ShoppingCartHelperP.PRODUCT_INDEX, position);
                startActivity(productDetailsIntent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the data
        if(mProductAdapter != null)
            {
                mProductAdapter.notifyDataSetChanged();
            }
        double subTotal = 0.00;
            for (Product p : mCartList) {
                int quantity = ShoppingCartHelperP.getProductQuantity(p);

                subTotal += p.price * quantity;
            }

            TextView productPriceTextView = (TextView) findViewById(R.id.TextViewSubtotal);
            productPriceTextView.setText("Subtotal: RM" + String.format("%.2f", subTotal));
        total = String.format("%.2f", subTotal);

    }

    public void checkout (View v) {
        Intent payment = new Intent(getBaseContext(), PaymentActivity.class);
        payment.putExtra("subtotal", total);
        startActivity(payment);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }
}
