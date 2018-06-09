package com.people.initials;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.people.R;
import com.people.utils.AppConstants;
import com.people.utils.PreferencesManager;

public class RoleSelectionActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_title,tv_voter,tv_leader;
    private Intent intent;
    private int role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        initUI();
        initListener();
    }

    public void initUI()
    {
        Typeface font = Typeface.createFromAsset(getAssets(), "Caviar_Dreams_Bold.ttf");

        tv_title=(TextView)findViewById(R.id.tv_title);
        tv_voter=(TextView)findViewById(R.id.tv_voter);
        tv_leader=(TextView)findViewById(R.id.tv_leader);
        tv_title.setTypeface(font);
        tv_voter.setTypeface(font);
        tv_leader.setTypeface(font);
    }

    public void initListener()
    {
        tv_voter.setOnClickListener(this);
        tv_leader.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        PreferencesManager manager = PreferencesManager.getInstance(this);
        switch(v.getId())
        {
            case R.id.tv_voter:
                intent = new Intent(RoleSelectionActivity.this, LoginActivity.class);
                intent.putExtra(AppConstants.PREF_ROLE, AppConstants.VOTER);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                manager.putInt(AppConstants.PREF_ROLE,AppConstants.VOTER);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_leader:
                intent = new Intent(RoleSelectionActivity.this, LoginActivity.class);
                intent.putExtra(AppConstants.PREF_ROLE, AppConstants.LEADER);
                manager.putInt(AppConstants.PREF_ROLE,AppConstants.LEADER);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
        }
    }
}
