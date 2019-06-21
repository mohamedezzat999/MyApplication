package com.example.macbookair.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RequestWorkerActivity extends AppCompatActivity {

    private Button accept , decline , acceptt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_worker);
        getWindow().getAttributes().windowAnimations = R.style.Fade;

        displayDialog();

    }

    private void displayDialog()
    {
        final AlertDialog d = new AlertDialog.Builder(this)
                .setTitle("NEW REQUEST")
                .setIcon(R.drawable.logotwo)
                .setView(R.layout.dialogefeedbacklayout)
                .create();
        d.show();


        accept= (Button) d.findViewById(R.id.btn_acc);
        decline = (Button) d.findViewById(R.id.btn_dec) ;

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(RequestWorkerActivity.this, "ACCEPT", Toast.LENGTH_LONG).show();
                d.hide();
                startActivity(new Intent(getApplicationContext() , SelectWorkerActivity.class));
                displayDialogTwo();
            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(RequestWorkerActivity.this, "DECLINE", Toast.LENGTH_LONG).show();
                d.hide();
                displayDialogTwo();


            }
        });



    }
    private void displayDialogTwo()
    {
        final AlertDialog dd = new AlertDialog.Builder(this)
                .setTitle("Report")
                .setIcon(R.drawable.logotwo)
                .setView(R.layout.dialogefeedbacklayout_report)
                .create();
        dd.show();


        acceptt= (Button) dd.findViewById(R.id.btn_dec);


        acceptt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(RequestWorkerActivity.this, "REPORT YA RO7MAK", Toast.LENGTH_LONG).show();
                dd.hide();
                startActivity(new Intent(getApplicationContext() , SelectWorkerActivity.class));
            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(RequestWorkerActivity.this, "DECLINE YA RO7MAK", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext() , SelectWorkerActivity.class));
    }
}
