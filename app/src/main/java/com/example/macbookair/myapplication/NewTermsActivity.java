package com.example.macbookair.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewTermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_terms);
        getSupportActionBar().setTitle("Terms");
    }
}
