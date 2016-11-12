package com.ohmydog.juliahou.ohmydog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by juliahou on 11/12/16.
 */

public class Quiz extends AppCompatActivity {

    private TextView tvQuestion;
    private TextView tvTokens;
    private RadioButton rbTrue;
    private JSONArray qSet;
    private boolean ans;
    private int qInd = 0;

    private int tokens;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        MobileAds.initialize(this, "ca-app-pub-8320692382610221/7820842994");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        tvTokens = (TextView) findViewById(R.id.tvTokens);
        rbTrue = (RadioButton) findViewById(R.id.rbTrue);

        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        tokens = prefs.getInt("tokens", 0);
        tvTokens.setText("" + tokens);

        qSet = fetchQuestions(read("questions.txt"));
        populateQuiz(qInd);
    }

    public void submit(View v) {
        if(rbTrue.isSelected() == ans) {
            tokens++;
            tvTokens.setText("" + tokens);
        }
        Random rand = new Random();
        qInd = rand.nextInt(qSet.length());
        populateQuiz(qInd);
    }

    private JSONArray fetchQuestions(String raw) {
        JSONArray res = null;
        try {
            JSONObject obj = new JSONObject(raw);
            res = obj.getJSONArray("list");
            System.out.println(""+res.length());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Toast.makeText(getApplicationContext(), "error reading questions", Toast.LENGTH_LONG).show();
            finish();
        }
        return res;
    }

    private void populateQuiz(int ind) {
        try {
            JSONObject q = qSet.getJSONObject(ind);
            tvQuestion.setText(q.getString("question"));
            ans = q.getBoolean("answer");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Toast.makeText(getApplicationContext(), "error reading questions", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private String read(String filename) {
        BufferedReader reader = null;
        String result = "";
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(filename), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                result += mLine;
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return result;
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
        editor.putInt("tokens", tokens);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
