package com.example.macbookair.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.loading.rotate.RotateLoading;

public class WelcomeActivity extends AppCompatActivity {

    private ImageView imageView ;


    RotateLoading rotateLoading ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        imageView = (ImageView) findViewById(R.id.imageView) ;


        rotateLoading = (RotateLoading) findViewById(R.id.rotateloading);

        rotateLoading.start();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // do something
                Intent intent = new Intent(WelcomeActivity.this, SelectionActivity.class);
                // If you just use this that is not a valid context. Use ActivityName.this
                startActivity(intent); }
        }, 1000);




    }



}
