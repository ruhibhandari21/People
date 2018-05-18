package com.people.initials;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.people.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Typeface font = Typeface.createFromAsset(getAssets(), "Caviar_Dreams_Bold.ttf");
        ((TextView)findViewById(R.id.textView)).setTypeface(font);
        new CountDownTimer(2000, 1000) {
            public void onFinish() {

                Intent intent = new Intent(SplashActivity.this, RoleSelectionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }

            public void onTick(long millisUntilFinished) {
            }

        }.start();
    }

}
