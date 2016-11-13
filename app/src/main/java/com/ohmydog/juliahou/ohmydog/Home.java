package com.ohmydog.juliahou.ohmydog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by juliahou on 11/11/16.
 */

public class Home extends AppCompatActivity {

    private TextView title;
    private AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        title = (TextView) findViewById(R.id.title);
        title.setText("OH MY DOG");
    }

    public void play(View v) {
        Intent i = new Intent(this, Quiz.class);
        startActivity(i);
    }

    public void donate(View v) {
        Intent i = new Intent(this, Donate.class);
        startActivity(i);
    }

    public void about(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.manifesto).setTitle("Manifesto");
        dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog != null && dialog.isShowing()) dialog.dismiss();
    }
}
