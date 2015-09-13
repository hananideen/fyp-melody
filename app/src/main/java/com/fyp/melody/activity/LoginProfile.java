package com.fyp.melody.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fyp.melody.ApplicationLoader;
import com.fyp.melody.CustomRequest;
import com.fyp.melody.R;
import com.fyp.melody.VolleySingleton;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hananideen on 7/7/2015.
 */
public class LoginProfile extends ActionBarActivity {

    private Button Start;
    private EditText Username;
    private ImageView ProfilePhoto;
    private ImageButton EditProfilePhoto;
    private Uri selectedImage;
    private String userName, phoneNumber;

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_profile);

        phoneNumber = ApplicationLoader.getInstance().getSettingPrefFile().getString("phoneNumber", "");

        InitUI();

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = Username.getText().toString();
                if (userName.length() == 0) {
                    Toast.makeText(ApplicationLoader.getContext(), "Please insert your name", Toast.LENGTH_LONG).show();
                } else {
                    final ProgressDialog dialog = ProgressDialog.show(LoginProfile.this, "", "Getting you ready for Meal-o-dy", true);
                    final Intent intent = new Intent(LoginProfile.this, LoginPassword.class);
                    //new SummaryAsyncTask().execute((Void) null);
                    new Send().execute("http://mynetsys.com/restaurant/usercreate.php?phoneNumber="+phoneNumber+"&userName="+userName);
                    ApplicationLoader.getInstance().getSettingsPrefFileEditor().putString("userName", userName);
                    ApplicationLoader.getInstance().getSettingsPrefFileEditor().apply();
                    dialog.dismiss();
                    startActivity(intent);
                }
            }
        });

        EditProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPhoto();
            }
        });

    }

    public void InitUI(){

        Start = (Button) findViewById(R.id.Start);
        Username = (EditText) findViewById(R.id.ProfileUsername);
        ProfilePhoto = (ImageView) findViewById(R.id.profilePhoto);
        EditProfilePhoto = (ImageButton)findViewById(R.id.editProfileImage);
    }

    public void SelectPhoto(){
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                selectedImage = data.getData();
                performCrop();

            } else if (requestCode == 2) {
                selectedImage = data.getData();
                performCrop();

            } else if(requestCode == 3){
                Bundle extras = data.getExtras();
                Bitmap croppedImage = extras.getParcelable("data");
                Uri croppedImageUri = getImageUri(croppedImage);
                ProfilePhoto.setImageURI(croppedImageUri);
                ApplicationLoader.getInstance().getSettingsPrefFileEditor().putString("ProfilePic", croppedImageUri.toString());
                ApplicationLoader.getInstance().getSettingsPrefFileEditor().commit();


            }
        }
    }

    private void performCrop(){
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(selectedImage, "image/*");
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("outputX", 600);
        cropIntent.putExtra("outputY", 600);
        cropIntent.putExtra("return-data", true);
        startActivityForResult(cropIntent, 3);

    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(ApplicationLoader.getContext().getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void LoadDefaultSettings(){

    }


    class Send extends AsyncTask<String, Void, Boolean> {
        String result;
        @Override
        protected Boolean doInBackground(String... params) {
            try{
                URL url = new URL(params[0]);
                URLConnection connection = url.openConnection();
                HttpURLConnection conn = (HttpURLConnection)connection;
                int responseCode = conn.getResponseCode();
                if(responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is,"UTF-8");
                    BufferedReader reader = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + " sucess");
                    }
                    result = sb.toString();
                    return true;
                }
            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    class SummaryAsyncTask extends AsyncTask<Void, Void, Boolean> {

        private void postData(String num, String name) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://mynetsys.com/restaurant/usercreate.php?");

            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
                nameValuePairs.add(new BasicNameValuePair("phoneNumber", num));
                nameValuePairs.add(new BasicNameValuePair("userName", name));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
            }
            catch(Exception e)
            {
                Log.e("log_tag", "Error:  "+e.toString());
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            postData("0123456789", "Hanani");
            return null;
        }
    }

}
