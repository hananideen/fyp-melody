package com.fyp.melody;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType;
import com.google.i18n.phonenumbers.Phonenumber;


import java.util.List;
import java.util.Random;

/**
 * Created by Hananideen on 28/5/2015.
 */
public class LoginPhone extends ActionBarActivity {

    private static final String TAG = LoginPhone.class.getSimpleName();


    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private TextView chooseCountry;
    private EditText codefield, phonefield;
    private Button OkButton;
    private String countryname = null, countrycode = null, phoneNumber = null;
    private int Direction;
    boolean IgnoreOnTextChange = false;


    List<CountryList.Country> countryList;
    CountryList CL;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);

        settings = getSharedPreferences(ApplicationLoader.Settings_PREFS_NAME, 0);
        editor = settings.edit();
        Direction = 0;      // set it to the default value

        Intent callBackIntent = getIntent();
        Direction = callBackIntent.getIntExtra("direction", 0);


        // Initialize the view components of this activity
        InitUI();

        // get the country list
        CL = new CountryList();
        countryList = CL.ReadCountries();

        // upon clicking on Choose a Country
        chooseCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an intent and open a new activity for the country list
                Intent intent = new Intent(LoginPhone.this, ChooseCountry.class);
                countryname = chooseCountry.getText().toString();
                countrycode = codefield.getText().toString();
                startActivityForResult(intent, 1);

            }
        });


        //listen to the changes in the code field
        codefield.setImeOptions(EditorInfo.IME_ACTION_NEXT | EditorInfo.IME_FLAG_NO_FULLSCREEN);
        codefield.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // do nothing

                if(IgnoreOnTextChange) {
                    IgnoreOnTextChange = false;
                    return;
                }
//                IgnoreOnTextChange = true;
                countrycode=codefield.getText().toString();

                if (countrycode.length() == 0)
                {
                    chooseCountry.setText(R.string.ChooseCountry);
                } else{
                    countryname = CL.getCodemap().get(countrycode);
//                    Log.i("phoneLogin", country);
                    if (countryname != null){
                        Log.i("phoneLogin", countryname);
                        chooseCountry.setText(countryname);

                    }
                    else {
                        chooseCountry.setText("Invalid country code");
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                // after the text changed check on the country list and apply it here..
                // edit the text of choose country
                // make the phonefield focus true.



            }
        });

        phonefield = (EditText) findViewById(R.id.phonefield);
        phonefield.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_FULLSCREEN);
        //TODO check if this gonna work first without the editor listener.

        countryname = settings.getString("CountryName", "");
        countrycode = settings.getString("CountryCode", "");

        // checek if they are saved in the shared preferences
        if(countryname.isEmpty() || countrycode.isEmpty())
        {
            codefield.requestFocus();
        }else {
            IgnoreOnTextChange = true;
            chooseCountry.setText(countryname);
            codefield.setText(countrycode);
            phonefield.requestFocus();
        }





        OkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phoneNumber = phonefield.getText().toString();
                if(countrycode == null || countrycode.length()== 0){
                    Toast.makeText(getApplication(), "Please Choose a Country", Toast.LENGTH_LONG).show();
                } else if (phoneNumber == null || phoneNumber.length()==0) {
                    Toast.makeText(getApplication(), "Please Enter phone Number", Toast.LENGTH_LONG).show();
                }else {
                    if (countryname == null)
                    {
                        Toast.makeText(ApplicationLoader.getContext(), "Please Enter a Correct country code", Toast.LENGTH_LONG).show();
                    } else {
                        Log.v(TAG, "validating Number....");
                        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
                        Phonenumber.PhoneNumber numberProto = null;
                        try {
                            Log.d(TAG, CL.getLanguageMap().get(countryname));

                            numberProto = phoneUtil.parse(phoneNumber, CL.getLanguageMap().get(countryname));

                        }catch(NumberParseException e){
                            Log.e("PhoneLogin", e.getMessage());

                        }
                        boolean isValidNumber = phoneUtil.isValidNumber(numberProto);

                        if (isValidNumber){
                            Log.d("PhoneLogin", "Number is Valid");
                            if (phoneUtil.getNumberType(numberProto) == PhoneNumberType.MOBILE)
                            {
                                Log.d(TAG, "phone is "+PhoneNumberType.MOBILE.toString());
                                Log.d(TAG, "Number is "+phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL));

                                editor.putString("CountryName", countryname);
                                editor.putString("CountryCode", countrycode);
                                editor.apply();

                                Intent intent = new Intent(LoginPhone.this, SmsVerification.class);
                                intent.putExtra("phonenumber", phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL));
                                intent.putExtra("direction", Direction);
                                startActivity(intent);
                                finish();

                            } else{
                                Toast.makeText(ApplicationLoader.getContext(), "Please Enter a Valid Mobile Number", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "phone is not mobile " +phoneUtil.getNumberType(numberProto).toString());
                            }
                        } else {
                            Toast.makeText(ApplicationLoader.getContext(),"Phone number is not Valid", Toast.LENGTH_LONG).show();
                            Log.e("PhoneLogin", "Number is Invalid");
                        }
                    }


//                    editor.putBoolean("hasLoggedIn", true);
//                    editor.apply();
//                    // this is to finish the intent ..
//                    finish();

                }

            }
        });
    }

    public void InitUI(){
        chooseCountry = (TextView) findViewById(R.id.ChooseCountry);
        codefield = (EditText) findViewById(R.id.codefield);
        OkButton = (Button) findViewById(R.id.OkButton);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            countryname= data.getStringExtra("CountryName");
            countrycode = data.getStringExtra("CountryCode");


            if(countryname.isEmpty() || countrycode.isEmpty()) {
                // do nothing


            } else {
                IgnoreOnTextChange = true;
                editor.putString("CountryName", countryname);
                editor.putString("CountryCode", countrycode);
                editor.commit();
                chooseCountry.setText(countryname);
                codefield.setText(countrycode);
                phonefield.requestFocus();
            }
        }
    }

    public void SendMessageTo(String phoneNumber){
        Log.i(TAG, "Sending SMS to "+phoneNumber);
        Log.d(TAG, phoneNumber.substring(phoneNumber.length()-4));

        SmsManager manager = SmsManager.getDefault();
        int code = generateCode(Integer.parseInt(phoneNumber.substring(phoneNumber.length()-4)));
        manager.sendTextMessage(phoneNumber, null, ""+code, null, null);
        Log.i(TAG, "Msg send to "+phoneNumber);

    }
    public int generateCode(int min){
        int code;
        int max = 9999;
        Random rnd = new Random();
        code = rnd.nextInt(max- min)+min;
        Log.e(TAG, "Generated code : "+code);
        return code;
    }

}
