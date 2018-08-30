package com.people.initials;

import android.app.AlertDialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.people.ApiCalls;
import com.people.ProgressFragment;
import com.people.R;
import com.people.root.DashboardActivity;
import com.people.utils.AppConstants;
import com.people.utils.PreferencesManager;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements
        ApiCalls.ApiCallback {

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
        if(manager.getInt(AppConstants.Preference.PREF_ROLE) == AppConstants.VOTER){
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

        manager = PreferencesManager.getInstance(ProfileActivity.this);
        if(manager.getInt(AppConstants.Preference.PREF_ROLE) == AppConstants.VOTER){
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

    private void validate(){

        EditText firstName = getEditText(R.id.edFName);
        EditText lastName = getEditText(R.id.edLName);
      /*  if(firstName.getText().length() == 0){
            firstName.setError("First name should not be blank");
            return;
        }

        if(lastName.getText().length() == 0){
            lastName.setError("Last name should not be blank");
            return;
        }*/

        startActivity(new Intent(ProfileActivity.this, DashboardActivity.class));
    }

    private EditText getEditText(int id){
        return (EditText)findViewById(id);
    }

    private Spinner getSpinner(int id){
        return (Spinner) findViewById(id);
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

    private void apiCalls(String tag) {

        showProgress(false);
        Map<String, String> params = new HashMap<String, String>();
        ApiCalls apiCalls = new ApiCalls(this);

        switch (tag){

            case AppConstants.WebApi.GET_PROFILE:
                params.put("userId", getEditText(R.id.edt_terminal_id).getText().toString().trim());
                break;

            case AppConstants.WebApi.UPDATE_PROFILE:
                params.put("userId", getEditText(R.id.edt_otp).getText().toString());
                params.put("firstName", getEditText(R.id.edt_otp).getText().toString());
                params.put("lastName", getEditText(R.id.edt_otp).getText().toString());
                params.put("postCode", getEditText(R.id.edt_otp).getText().toString());
                params.put("stateCode", getEditText(R.id.edt_otp).getText().toString());
                params.put("districtCode", getEditText(R.id.edt_otp).getText().toString());
                params.put("taluka", getEditText(R.id.edt_otp).getText().toString());
                params.put("city", getEditText(R.id.edt_otp).getText().toString());
                params.put("postalCode", getEditText(R.id.edt_otp).getText().toString());
                params.put("MobileNo", getEditText(R.id.edt_terminal_id).getText().toString().trim());
                params.put("Role", PreferencesManager.getInstance(this).
                        getInt(AppConstants.Preference.PREF_ROLE)+"");
                break;

        }
        apiCalls.initiateRequest(Request.Method.POST, tag,new JSONObject(params), tag);
    }

    private void showProgress(boolean isDismiss){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        if(!isDismiss) {
            DialogFragment dialogFragment = new ProgressFragment();
            dialogFragment.show(ft, "dialog");
        }
    }

    @Override
    public void onResponse(JSONObject response, String TAG) {

        try {
            switch (TAG) {
                case AppConstants.WebApi.GET_PROFILE:
                    if (response != null && response.has("status")) {
                        if (response.getBoolean("status")) {
                            setProfileData(response);
                        }
                    }
                    break;
                case AppConstants.WebApi.UPDATE_PROFILE:
                    break;
            }
        }catch (Exception e){ e.printStackTrace(); }
    }

    @Override
    public void onError(VolleyError error, String TAG) {
        switch (TAG){
            case AppConstants.WebApi.GENERATE_OTP:
                break;
            case AppConstants.WebApi.LOGIN:
                break;
        }
    }

    private void setProfileData(JSONObject data) throws Exception{
        getEditText(R.id.edFName).setText(data.optString("firstName"));
        getEditText(R.id.edLName).setText(data.optString("lastName"));
        getEditText(R.id.edMobile).setText(data.optString("mobileNo"));
        getEditText(R.id.edTaluka).setText(data.optString("taluka"));
        getEditText(R.id.edCity).setText(data.optString("city"));
        getEditText(R.id.edCode).setText(data.optString("postalCode"));
        getSpinner(R.id.spinnerPost).setSelection(data.optInt("stateCode"));
        getSpinner(R.id.spinnerState).setSelection(data.optInt("districtCode"));
        getSpinner(R.id.spinnerDistrict).setSelection(data.optInt("postCode"));

        manager.putString(AppConstants.Preference.PREF_F_NAME, data.optString("firstName"));
        manager.putString(AppConstants.Preference.PREF_L_NAME,data.optString("lastName"));
        manager.putString(AppConstants.Preference.PREF_MOBILE,data.optString("mobileNo"));
        manager.putString(AppConstants.Preference.PREF_TALUKA,data.optString("taluka"));
        manager.putString(AppConstants.Preference.PREF_CITY,data.optString("city"));
        manager.putString(AppConstants.Preference.PREF_POSTAL_CODE,data.optString("postalCode"));
        manager.putInt(AppConstants.Preference.PREF_STATE_POS,data.optInt("stateCode"));
        manager.putInt(AppConstants.Preference.PREF_DISTRICT_POS,data.optInt("districtCode"));
        manager.putInt(AppConstants.Preference.PREF_POST_POS,data.optInt("postCode"));
    }

}
