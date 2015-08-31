package com.fyp.melody.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fyp.melody.R;

/**
 * Created by Hananideen on 16/8/2015.
 */
public class PaymentActivity extends Activity {

    Button cod, bank, cancel;
    TextView subtotal;
    String total;

    protected void onCreate (Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_payment);

        cod = (Button) findViewById(R.id.buttonCOD);
        bank = (Button) findViewById(R.id.buttonBank);
        cancel = (Button) findViewById(R.id.buttonCancel);
        subtotal = (TextView) findViewById(R.id.textViewTotal);

        Intent intent = getIntent();
        total = intent.getStringExtra("subtotal");
        subtotal.setText("Subtotal: RM" +total);

        cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, CheckoutActivity.class);
                intent.putExtra("subtotal", total);
                startActivity(intent);
                finish();
            }
        });

        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, PaymentBankActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
