package com.people.initials;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.people.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        initListener();
    }

    public void initUI()
    {
        tv_title=(TextView)findViewById(R.id.tv_title);
        Typeface font = Typeface.createFromAsset(getAssets(), "Caviar_Dreams_Bold.ttf");
        tv_title.setTypeface(font);
    }

    public void initListener()
    {
        tv_title.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
