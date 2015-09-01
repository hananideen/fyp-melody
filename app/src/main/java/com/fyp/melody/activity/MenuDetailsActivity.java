package com.fyp.melody.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.fyp.melody.Product;
import com.fyp.melody.R;
import com.fyp.melody.ShoppingCartHelperP;
import com.fyp.melody.VolleySingleton;
import com.fyp.melody.adapter.MenuAdapter;
import com.fyp.melody.helper.ShoppingCartHelper;
import com.fyp.melody.model.Menus;

import java.util.List;
import java.util.Vector;

/**
 * Created by Hananideen on 1/9/2015.
 */
public class MenuDetailsActivity extends AppCompatActivity {

    int id, position;
    String name, description, price,image;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_details);

        Intent menu = getIntent();
        position = menu.getIntExtra("position", 0);
        id =  menu.getIntExtra("id", 0);
        name = menu.getStringExtra("name");
        description = menu.getStringExtra("description");
        price = menu.getStringExtra("price");
        image = menu.getStringExtra("image");

        NetworkImageView productImageView = (NetworkImageView) findViewById(R.id.ImageViewProduct);
        TextView productTitleTextView = (TextView) findViewById(R.id.TextViewProductTitle);
        TextView productDetailsTextView = (TextView) findViewById(R.id.TextViewProductDetails);
        TextView productPriceTextView = (TextView) findViewById(R.id.TextViewProductPrice);
        TextView textViewCurrentQuantity = (TextView) findViewById(R.id.textViewCurrentlyInCart);

        productImageView.setImageUrl("http://mynetsys.com/restaurant/" +image, VolleySingleton.getInstance().getImageLoader());
        productTitleTextView.setText(name);
        productDetailsTextView.setText(description);
        productPriceTextView.setText("RM" + price);
//        textViewCurrentQuantity.setText("Currently in Cart: "
//                + ShoppingCartHelper.getProductQuantity(selectedMenu));
//
//        final EditText editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);
//
//        Button addToCartButton = (Button) findViewById(R.id.ButtonAddToCart);
//        addToCartButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                int quantity = 0;
//                try {
//                    quantity = Integer.parseInt(editTextQuantity.getText()
//                            .toString());
//                    if (quantity < 0) {
//                        Toast.makeText(getBaseContext(),
//                                "Please enter a quantity of 0 or higher",
//                                Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                } catch (Exception e) {
//                    Toast.makeText(getBaseContext(),
//                            "Please enter a numeric quantity",
//                            Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                ShoppingCartHelper.setQuantity(selectedMenu, quantity);
//                finish();
//            }
//        });

    }

}

