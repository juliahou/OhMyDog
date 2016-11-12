package com.ohmydog.juliahou.ohmydog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by juliahou on 11/11/16.
 */

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void play(View v) {
        Intent i = new Intent(this, Quiz.class);
        startActivity(i);
    }

    public void donate(View v) {

    }

    public void about(View v) {

    }
}
