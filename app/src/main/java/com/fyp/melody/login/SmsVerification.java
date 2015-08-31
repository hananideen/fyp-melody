package com.fyp.melody.login;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.fyp.melody.ApplicationLoader;
import com.fyp.melody.R;
import com.fyp.melody.Restaurant;
import com.fyp.melody.activity.LoginPhone;
import com.fyp.melody.activity.LoginProfile;

import java.util.Random;
/**
 * Created by Hananideen on 28/5/2015.
 */
public class SmsVerification extends Activity {

    private static final String TAG = SmsVerification.class.getSimpleName();

    private static TextView info_text_1, info_text_2, info_text_3;
    private static TextView phone_number_view;
    private static ImageButton edit_ph_button;
    private static Button Submit;
    private static EditText verify_code;
    private static ProgressBar progressBar;
    private static String phoneNumber;
    private static boolean isDoneVerifying = false;
    private static boolean isVerified = false;
    private static String Code, SmsCode;
    private static CountDownTimer countDownTimer;
    private int Direction;
    public static final String Settings_PREFS_NAME = "SettingsFile";
    SharedPreferences settings;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_verification);
        final long millisInFuture = 30*1000;
        final long countDownInterval = 1000;
        settings = getSharedPreferences(Settings_PREFS_NAME, 0);
        editor = settings.edit();
        Direction = 0;
        InitUI();



        final Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phonenumber");
        Direction = intent.getIntExtra("direction", 0);

        verify_code.setVisibility(View.GONE);
        phone_number_view.setText(phoneNumber);
        progressBar.setMax((int) (millisInFuture / 1000));
        progressBar.setProgress(0);


        countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progressStatus = (int)((millisInFuture/1000)-(millisUntilFinished/1000));
                progressBar.setProgress(progressStatus);
                if (isDoneVerifying){
                    if (isVerified){
                        Log.d(TAG, "i'm at countdown");
                        StopReceiver();
                        updateUI(true);

                    }
                }
            }
            @Override
            public void onFinish() {
                StopReceiver();
                updateUI(false); // have to be set to false
                //TODO perform verification by call. to be checked later
            }
        };

        countDownTimer.start();
        Code = SendMessageTo(phoneNumber);

        verify_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp;
                temp = verify_code.getText().toString();
                if (temp.length() == 0){
                    Submit.setClickable(false);
                } else {
                    Submit.setClickable(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = verify_code.getText().toString();
                if (temp.length()!=0){
                    if (temp.equals(Code) || isVerified || temp.equals(SmsCode))
                    {
                        Toast.makeText(ApplicationLoader.getContext(), "the code is correct", Toast.LENGTH_LONG).show();
                        // save the phone number in the sharedPreference and parse it into international format without separators
                        ApplicationLoader.getInstance().getSettingsPrefFileEditor().putString("phoneNumber", PhoneNumberUtils.stripSeparators(phoneNumber));

                        if (Direction == 0){
                            Intent intnt = new Intent(SmsVerification.this, LoginProfile.class);
                            ApplicationLoader.getInstance().getSettingsPrefFileEditor().putBoolean("hasLoggedIn", true);
                            ApplicationLoader.getInstance().getSettingsPrefFileEditor().apply();
                            countDownTimer.cancel();
                            startActivity(intnt);

                        } else {
                            countDownTimer.cancel();
                        }
                        finish();

                    } else
                        Toast.makeText(ApplicationLoader.getContext(), "the code is not correct", Toast.LENGTH_LONG ).show();
                }
            }
        });

        edit_ph_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                Intent intent1 = new Intent(SmsVerification.this, LoginPhone.class);
                startActivity(intent1);
                finish();
            }
        });

        info_text_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ApplicationLoader.getContext(), "The call feature is not yet implemented.", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Log.e(TAG, "Back key is pressed");
            Intent intent = new Intent(this, Restaurant.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }



    public void InitUI(){
        info_text_1 = (TextView) findViewById(R.id.info_text_1);
        info_text_2 = (TextView) findViewById(R.id.info_text_2);
        info_text_3 = (TextView) findViewById(R.id.info_text_3);
        phone_number_view = (TextView) findViewById(R.id.phone_number_view);
        edit_ph_button = (ImageButton) findViewById(R.id.edit_phone_number_button);
        verify_code = (EditText) findViewById(R.id.verify_code);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        Submit = (Button) findViewById(R.id.submit_code);
        Submit.setClickable(false);
        info_text_3.setClickable(false);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "Resumed");
        StartReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "Destroyed");
        countDownTimer.cancel();
        StopReceiver();
    }

    public String SendMessageTo(String phoneNumber){
        Log.i(TAG, "Sending SMS to " + phoneNumber);
        Log.d(TAG, phoneNumber.substring(phoneNumber.length()-4));
        int code = 0;
        try{
            SmsManager manager = SmsManager.getDefault();
            code = generateCode(Integer.parseInt(phoneNumber.substring(phoneNumber.length()-4)));
            //TODO uncomment to send msgs
            manager.sendTextMessage(phoneNumber, null, ""+code, null, null);
            Log.i(TAG, "Msg sent to "+phoneNumber);



        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "failed to send the Msg");
        }

        return Integer.toString(code);

    }
    public int generateCode(int min){
        int code;
        int max = 9999;
        Random rnd = new Random();
        code = rnd.nextInt(max- min)+min;
        Log.e(TAG, "Generated code : "+Integer.toString(code));
        return code;
    }

    public static void updateUI(boolean state){
        if (state){
            countDownTimer.cancel();
            progressBar.setVisibility(View.GONE);
            info_text_1.setText(R.string.smsReceived);
            verify_code.setVisibility(View.VISIBLE);
            verify_code.setText(null);
            verify_code.setText(Code);
            info_text_2.setText(R.string.VerifiedText);
            info_text_3.setVisibility(View.GONE);
            Submit.setClickable(true);
            Submit.requestFocus();
        } else {
            countDownTimer.cancel();
            progressBar.setVisibility(View.GONE);
            info_text_1.setText(R.string.smsReceived);
            verify_code.setVisibility(View.VISIBLE);
            verify_code.setText(null);
            info_text_2.setText(R.string.VerifiedText);
            info_text_3.setText("Call Me!");
            info_text_3.setTextColor(Color.BLUE);
            info_text_3.setClickable(true);
            //info_text_3.setVisibility(View.GONE);
            //TODO Update the UI to make call
        }
    }


    public static class SmsReceiver extends BroadcastReceiver {

        SmsManager manager = SmsManager.getDefault();
        @Override
        public void onReceive(Context context, Intent intent) {
            final Bundle bundle = intent.getExtras();
            Log.i(TAG, "Message received");
            try {
                if(bundle != null){
                    final Object[] pdusObj = (Object[])bundle.get("pdus");
                    for(int i =0; i<pdusObj.length;i++){
                        SmsMessage currentMsg = SmsMessage.createFromPdu((byte[])pdusObj[i]);
                        String ph = currentMsg.getDisplayOriginatingAddress();
                        SmsCode = currentMsg.getDisplayMessageBody();

                        Log.d(TAG, "phone number is "+ph);
                        Log.d(TAG, "Message is "+ SmsCode);
                        if (PhoneNumberUtils.compare(ph, phoneNumber)){
                            Log.e(TAG, "Matched");

                            if (Code.equals(SmsCode)){
                                isDoneVerifying = true;
                                isVerified = true;
                                break;
                            }
                        }
                    }
                }
            }catch(Exception e ){
                e.printStackTrace();
            }
        }
    }

    public void StopReceiver(){
        PackageManager pm = ApplicationLoader.getInstance().getPackageManager();
        ComponentName componentName = new ComponentName(SmsVerification.this, SmsReceiver.class);
        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }
    public void StartReceiver(){
        PackageManager pm = ApplicationLoader.getInstance().getPackageManager();
        ComponentName componentName = new ComponentName(SmsVerification.this, SmsReceiver.class);
        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }
}

