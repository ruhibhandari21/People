package com.people.initials;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.people.R;
import com.people.root.DashboardActivity;
import com.people.utils.AppConstants;
import com.people.utils.PreferencesManager;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    String imgDecodeString = "";
    ImageView profilePic;
    private DisplayImageOptions options;
    PreferencesManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findViewById(R.id.btn_reg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        manager = PreferencesManager.getInstance(ProfileActivity.this);
        if(manager.getInt(AppConstants.PREF_ROLE) == AppConstants.VOTER){
            findViewById(R.id.tvPost).setVisibility(View.GONE);
            findViewById(R.id.spinnerPost).setVisibility(View.GONE);
        }

        profilePic = (ImageView) findViewById(R.id.profilePic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!manager.getString("profile").equalsIgnoreCase("")){
                    showLogoDialog();
                }else{
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 1);
                }

            }
        });
        options = new DisplayImageOptions.Builder()
                .displayer(new CircleBitmapDisplayer())
                .cacheInMemory(true).cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.
                Builder(this).build();

        ImageLoader.getInstance().init(config);
        if(!manager.getString("mobile").equalsIgnoreCase("")){
            EditText mobile = getEditText(R.id.edMobile);
            mobile.setText(manager.getString("mobile"));
            mobile.setEnabled(false);
        }
    }

    private void initUI(){

    }

    private void initListener(){

    }

    private void validate(){

        EditText firstName = getEditText(R.id.edFName);
        EditText lastName = getEditText(R.id.edLName);
        if(firstName.getText().length() == 0){
            firstName.setError("First name should not be blank");
            return;
        }

        if(lastName.getText().length() == 0){
            lastName.setError("Last name should not be blank");
            return;
        }

        startActivity(new Intent(ProfileActivity.this, DashboardActivity.class));
    }

    private EditText getEditText(int id){
        return (EditText)findViewById(id);
    }

    public void showLogoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater lf = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        builder.setView(lf.inflate(R.layout.dialog_profile_pic, null));
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        TextView remove_tv = (TextView) dialog.findViewById(R.id.remove_tv);
        TextView add_tv = (TextView) dialog.findViewById(R.id.add_tv);
        TextView cancel_tv = (TextView) dialog.findViewById(R.id.cancel_tv);

        add_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);

            }
        });
        remove_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                profilePic.setImageDrawable(null);
                imgDecodeString = "";
            }
        });
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //-- Return from Signature Capture
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            // Get the Image from data
            processImage(data);

        }
    }

    private void processImage(Intent data){
        Bitmap imageBitmap = null;
        Bundle extras = data.getExtras();
        if(extras == null){
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),
                        data.getData());
                int nh = (int) ( imageBitmap.getHeight() * (512.0 / imageBitmap.getWidth()) );
                imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 512, nh, true);
            }catch (Exception e){e.printStackTrace();}
        }else {
            imageBitmap = (Bitmap) extras.get("data");
            if(imageBitmap == null){
                ArrayList arrayList = (ArrayList) extras.get("selectedItems");
                try {

                    imageBitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().
                            getContentResolver(), (Uri) arrayList.get(0));
                    int nh = (int) ( imageBitmap.getHeight() * (512.0 / imageBitmap.getWidth()) );
                    imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 512, nh, true);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(imageBitmap == null){
                    try {
                        imageBitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                        int nh = (int) ( imageBitmap.getHeight() * (512.0 / imageBitmap.getWidth()) );
                        imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 512, nh, true);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        imgDecodeString = saveToInternalStorage(imageBitmap);
        ImageLoader.getInstance().displayImage("file://" + imgDecodeString, profilePic,
                options);
        PreferencesManager manager = PreferencesManager.getInstance(this);
        manager.putString("profile",imgDecodeString);
    }
    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File myPath = new File(directory, "item_" + System.currentTimeMillis() + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return myPath.getAbsolutePath();
    }

}
