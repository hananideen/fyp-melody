package com.fyp.melody;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

/**
 * Created by Hananideen on 1/6/2015.
 */
public class SettingsProfile extends ActionBarActivity {

    private Uri selectedImage;

    private ImageView viewImage;
    private ImageButton button;
    private EditText viewName;
    private Button saveName;

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_profile);

        settings = getSharedPreferences(ApplicationLoader.Settings_PREFS_NAME, 0);
        editor = settings.edit();

        viewImage = (ImageView)findViewById(R.id.profilePhoto);
        String image = settings.getString("ProfilePic", null);
        if(image != null) {
            viewImage.setImageURI(Uri.parse(image));
        }

        button = (ImageButton)findViewById(R.id.editProfileImage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        viewName = (EditText) findViewById(R.id.ProfileUsername);
        viewName.setText(settings.getString("userName", ""));

        saveName = (Button) findViewById(R.id.editUserName);
        saveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("userName", viewName.getText().toString());
                editor.commit();
                Toast.makeText(getApplicationContext(), "Username changed.", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
                Uri croppedImageUri = getImageUri(SettingsProfile.this, croppedImage);
                viewImage.setImageURI(croppedImageUri);
                editor.putString("ProfilePic", croppedImageUri.toString());
                editor.commit();
            }
        }
    }

    private void performCrop(){
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(selectedImage, "image/*");
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("outputX", 500);
        cropIntent.putExtra("outputY", 500);
        cropIntent.putExtra("return-data", true);
        startActivityForResult(cropIntent, 3);

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}
