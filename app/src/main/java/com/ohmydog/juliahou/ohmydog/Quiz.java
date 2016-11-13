package com.ohmydog.juliahou.ohmydog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
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
    private CardView cardView;
    private JSONArray qSet;
    private boolean ans;
    private int qInd = 0;

    private int tokens;

    private AnimationSet as;

    public static final String MY_PREFS_NAME = "MyPrefsFile";

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
        cardView = (CardView) findViewById(R.id.card_view);

        as = new AnimationSet(true);
        Animation slideRight = AnimationUtils.loadAnimation(this, R.anim.slide_right);
        Animation fade = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        as.addAnimation(slideRight);
        as.addAnimation(fade);

        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        tokens = prefs.getInt("tokens", 0);
        tvTokens.setText("" + tokens);

        qSet = fetchQuestions(read("questions.txt"));
        Random rand = new Random();
        qInd = rand.nextInt(qSet.length()-1);
        populateQuiz(qInd);
    }

    public void submit(View v) {
        if(rbTrue.isChecked() == ans) {
            tokens++;
            tvTokens.setText("" + tokens);
            SharedPreferences.Editor editor = getSharedPreferences("meow", Context.MODE_PRIVATE).edit();
            editor.putInt("tokens", tokens);
            editor.apply();
        }
        Random rand = new Random();
        qInd = rand.nextInt(qSet.length()-1);
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
            Toast.makeText(getApplicationContext(), "error fetching questions", Toast.LENGTH_LONG).show();
            finish();
        }
        return res;
    }

    private void populateQuiz(final int ind) {
        as.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                try {
                    JSONObject q = qSet.getJSONObject(ind);
                    tvQuestion.setText(q.getString("question"));
                    ans = q.getBoolean("answer");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    Log.e("quiz", e.getMessage() + " " + e.getCause() + ind);
                    Toast.makeText(getApplicationContext(), "error populating quiz at index:" + ind, Toast.LENGTH_LONG).show();
                    finish();
                }
                cardView.setTranslationX(0);
                cardView.setAlpha(1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        cardView.startAnimation(as);


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
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
