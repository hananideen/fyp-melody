package com.fyp.melody.activity;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fyp.melody.adapter.MenuAdapter;
import com.fyp.melody.model.Menus;
import com.fyp.melody.R;
import com.fyp.melody.helper.ShoppingCartHelper;

import java.util.List;

/**
 * Created by Hananideen on 16/9/2015.
 */
public class ShoppingCartActivity extends AppCompatActivity {

    private List<Menus> mCartList;
    private MenuAdapter mMenuAdapter;
    String total;

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
        mMenuAdapter = new MenuAdapter(mCartList, getLayoutInflater(), true);
        listViewCatalog.setAdapter(mMenuAdapter);

        listViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
        long id) {
        Intent productDetailsIntent = new Intent(getBaseContext(), MenuDetailsActivity.class);
        productDetailsIntent.putExtra("position", position);
        startActivity(productDetailsIntent);
        }
        });

        }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the data
        if(mMenuAdapter != null)
        {
        mMenuAdapter.notifyDataSetChanged();
        }
        double subTotal = 0.00;
        for (Menus p : mCartList) {
        int quantity = ShoppingCartHelper.getProductQuantity(p);

        subTotal += p.MenuPrice * quantity;
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


}
