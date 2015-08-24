package com.fyp.melody;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Hananideen on 16/8/2015.
 */
public class PaymentBankActivity extends Activity {

    Button confirm, cancel;
    EditText ref, time;

    protected void onCreate (Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_payment_bank);

        confirm = (Button) findViewById(R.id.buttonConfirm);
        cancel = (Button) findViewById(R.id.buttonCancel);
        ref = (EditText) findViewById(R.id.ediRef);
        time = (EditText) findViewById(R.id.editTime);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reff = ref.getText().toString();
                String times = time.getText().toString();
                if (reff.length()==0 && times.length()==0){
                    Toast.makeText(PaymentBankActivity.this, "Please insert your Reference No", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(PaymentBankActivity.this, "Payment confirmed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PaymentBankActivity.this, CheckoutActivity.class);
                    startActivity(intent);
                    finish();
                }
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
