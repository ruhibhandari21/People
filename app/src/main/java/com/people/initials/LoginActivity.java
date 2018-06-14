package com.people.initials;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.people.R;
import com.people.utils.AppConstants;
import com.people.utils.OkHttpHandler;
import com.people.utils.OnTaskCompleted;
import com.people.utils.PreferencesManager;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnTaskCompleted {

    private TextView tv_title;
    private Button btn_send_otp,btn_login;
    private CheckBox chk_vise;
    private LinearLayout ll_otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        PreferencesManager manager = PreferencesManager.getInstance(this);
        manager.putString("mobile", "");
        manager.putString("profile","");
        initUI();
        initListener();
    }

    public void initUI(){
        tv_title=(TextView)findViewById(R.id.tv_title);
        Typeface font = Typeface.createFromAsset(getAssets(), "Caviar_Dreams_Bold.ttf");
        tv_title.setTypeface(font);
        btn_send_otp=(Button)findViewById(R.id.btn_send_otp);
        chk_vise=(CheckBox)findViewById(R.id.chk_vise);
        btn_login=(Button)findViewById(R.id.btn_login);
        ll_otp=(LinearLayout)findViewById(R.id.ll_otp);
        btn_send_otp.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);
        chk_vise.setVisibility(View.GONE);
        ll_otp.setVisibility(View.GONE);
    }

    public void initListener(){
        btn_login.setOnClickListener(this);
        btn_send_otp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_login:
                EditText edt_otp = getEditText(R.id.edt_otp);
                PreferencesManager manager = PreferencesManager.getInstance(this);
                manager.putString("mobile", getEditText(R.id.edt_terminal_id).getText().toString());
                if(edt_otp.getText().length() == 5 && chk_vise.isChecked()) {
                    startActivity(new Intent(this, ProfileActivity.class));
                    btn_send_otp.setVisibility(View.GONE);
                }else{
                    if(edt_otp.getText().length() < 5)
                        edt_otp.setError("Enter 5 digit OTP");
                    else
                    Toast.makeText(this, "Please select terms and condition box.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_send_otp:
                EditText mobile = getEditText(R.id.edt_terminal_id);
                if(mobile.getText().length() != 10){
                    mobile.setError("Invalid mobile no");
                }else{
                    btn_send_otp.setVisibility(View.GONE);
                    btn_login.setVisibility(View.VISIBLE);
                    chk_vise.setVisibility(View.VISIBLE);
                    ll_otp.setVisibility(View.VISIBLE);
                }

                break;

        }
    }

    private EditText getEditText(int id){
        return (EditText)findViewById(id);
    }

    private void callService(String API){
        HashMap<String,String> requestMap = new HashMap();
        PreferencesManager manager = PreferencesManager.getInstance(this);
        String requestUrl = AppConstants.BASE_URL + API;
        switch(API){
            case AppConstants.GENERATE_OTP:
                requestMap.put("MobileNo", getEditText(R.id.edt_terminal_id).getText().toString());
                requestMap.put("Role", manager.getInt(AppConstants.PREF_ROLE)+"");
                break;

            case AppConstants.LOGIN:
                requestMap.put("MobileNo", getEditText(R.id.edt_otp).getText().toString());
                requestMap.put("Role", manager.getInt(AppConstants.PREF_ROLE)+"");
                requestMap.put("Otp", getEditText(R.id.edt_terminal_id).getText().toString());
                break;

           }

        OkHttpHandler okHttpHandler = new OkHttpHandler(this, this, requestMap, API);
        okHttpHandler.execute(requestUrl);

    }

    @Override
    public void onTaskCompleted(String result, String TAG) throws Exception {

        switch(TAG){

        }

    }
}
