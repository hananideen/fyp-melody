package com.fyp.melody;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Hananideen on 16/8/2015.
 */
public class PaymentActivity extends Activity {

    Button cod, bank, cancel;

    protected void onCreate (Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_payment);

        cod = (Button) findViewById(R.id.buttonCOD);
        bank = (Button) findViewById(R.id.buttonBank);
        cancel = (Button) findViewById(R.id.buttonCancel);

        cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, CheckoutActivity.class);
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
