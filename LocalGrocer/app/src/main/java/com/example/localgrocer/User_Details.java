package com.example.localgrocer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_Details extends AppCompatActivity {
    private CircleImageView profile_pic;
    private CardView edit_profile_pic;
    private Uri profilepicfilePath;
    private String image_path = "";
    private UploadTask uploadTask;
    private EditText name, phone_number, email_id;
    private Button sign_up_button;
    private static final int PICK_IMAGE_REQUEST = 234;
    private Activity activity;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = database.getReference("Users");

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__details);
        activity = this;

        //Analytics Initialization
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //Initialize Views
        edit_profile_pic = findViewById(R.id.edit_profile_pic_button_signup);
        name = findViewById(R.id.name_signup);
        email_id = findViewById(R.id.email_signup);
        sign_up_button = findViewById(R.id.SignUp_Button);
        phone_number = findViewById(R.id.phone_number_signup);

        //Set Profile Picture
        AssetManager assetManager = getAssets();
        profile_pic = findViewById(R.id.profile_image_signup);
        try {
            InputStream ims = assetManager.open("default_avatar.png");
            Drawable d = Drawable.createFromStream(ims, null);
            profile_pic.setImageDrawable(d);
        } catch (IOException ex) {

        }

        edit_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentfile = new Intent();
                intentfile.setType("image/*");
                intentfile.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intentfile, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Signing Up");
                progressDialog.setCancelable(false);
                progressDialog.show();
                try {
                    if (!name.getText().toString().trim().equals("") && !email_id.getText().toString().equals("")) {
                        userRef.child(phone_number.getText().toString().trim()).child("Name").setValue(name.getText().toString().trim());
                        userRef.child(phone_number.getText().toString().trim()).child("Email").setValue(email_id.getText().toString().trim());
                        if (image_path.equals("")) {
                            userRef.child(phone_number.getText().toString().trim()).child("Profile Picture").setValue("-1");
                        } else {
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            UUID uuid = UUID.randomUUID();
                            String randomUUIDString = uuid.toString();
                            StorageReference storageReference = storage.getReference().child(image_path = ("profile_images/" + randomUUIDString.trim() + image_path.substring(image_path.lastIndexOf('.'), image_path.length())));
                            uploadTask = storageReference.putFile(profilepicfilePath);
                            userRef.child(phone_number.getText().toString().trim()).child("Profile Picture").setValue(image_path);
                        }
                        progressDialog.dismiss();
                        Intent intent = new Intent(activity, ConsumerActivity.class);
                        startActivity(intent);
                        activity.finish();
                    } else {
                        progressDialog.dismiss();
                        Snackbar.make(view, "Please enter all the fields.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Snackbar.make(view, "An error occured during sign-up. Please try again later.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });

        //Set Phone Number
        phone_number.setText(this.getIntent().getStringExtra("Phone Number"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            profilepicfilePath = data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), profilepicfilePath);
                //pictureshow.setVisibility(View.VISIBLE);
                profile_pic.setImageBitmap(bitmap);
                //back_img_path.setText(filePath.getLastPathSegment() +"." + getContentResolver().getType(filePath).substring(getContentResolver().getType(filePath).lastIndexOf('/')+1));
                image_path = profilepicfilePath.getLastPathSegment() +"." + getContentResolver().getType(profilepicfilePath).substring(getContentResolver().getType(profilepicfilePath).lastIndexOf('/')+1);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
