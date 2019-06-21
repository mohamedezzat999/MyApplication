package com.example.macbookair.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class SelectionActivity extends AppCompatActivity {

    private ImageButton client , worker;
    private ImageView car ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        client = (ImageButton) findViewById(R.id.imageButtonClient) ;
        worker = (ImageButton) findViewById(R.id.imageButtonWorker) ;
        car = (ImageView) findViewById(R.id.imageButtonCar) ;

        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SelectionActivity.this , LoginClientActivity.class) ;

                startActivity(i);

            }
        });

        worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SelectionActivity.this , LoginWorkerActivity.class) ;

                startActivity(i);

            }
        });

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SelectionActivity.this , DeviceList.class) ;

                startActivity(i);

            }
        });



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();

        System.exit(0);
    }
}

