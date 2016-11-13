package com.ohmydog.juliahou.ohmydog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by juliahou on 11/12/16.
 */

public class Donate extends AppCompatActivity {

    private TextView bank;
    private TextView tvFood;
    private TextView tvMed;
    private TextView tvAdopt;

    private int tokens;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        bank = (TextView) findViewById(R.id.tvBank);
        SharedPreferences prefs = getSharedPreferences("meow", Context.MODE_PRIVATE);
        tokens = prefs.getInt("tokens", 0);
        bank.setText("" + tokens);

//        tvFood.setText("Food and Water");
//        tvMed.setText("Medicine");
//        tvAdopt.setText("Adoption");

        final Button button_food = (Button) findViewById(R.id.button_food);
        button_food.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tokens >= 50) {
                    tokens = tokens - 50;
                    bank.setText("" + tokens);
                }
            }
        });

        final Button button_med = (Button) findViewById(R.id.button_med);
        button_med.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tokens >= 100) {
                    tokens = tokens - 100;
                    bank.setText("" + tokens);
                }
            }
        });

        final Button button_adopt = (Button) findViewById(R.id.button_adopt);
        button_adopt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tokens >= 150) {
                    tokens = tokens - 150;
                    bank.setText("" + tokens);
                }
            }
        });
    }

}
