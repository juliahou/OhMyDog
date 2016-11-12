package com.ohmydog.juliahou.ohmydog;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new CountDownTimer(1000, 1000) {
            public void onFinish() {
                Intent i = new Intent(getApplicationContext(), Home.class);
                startActivity(i);
            }
            public void onTick(long millisUntilFinished) { }
        }.start();
    }
}
