package com.example.macbookair.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ClientShowActivity extends AppCompatActivity {

    TextView workerName, workerPhone, workerGender, workerAddress  , workerEmail;
    Button call , cancel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_show);
        getSupportActionBar().setTitle("Client Profile");



        workerName = (TextView) findViewById(R.id.WorkerName);
        workerAddress = (TextView)findViewById(R.id.WorkerAddress);
        workerPhone = (TextView)findViewById(R.id.WorkerPhone);
        workerGender = (TextView)findViewById(R.id.ClientGender);
        workerEmail = (TextView)findViewById(R.id.ClientEmail);
        call = (Button) findViewById(R.id.call_btn);
        cancel = (Button) findViewById(R.id.cancel) ;



        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", workerPhone.getText().toString(), null));
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialogTwo();
            }
        });



    }
    private void displayDialogTwo()
    {
        final AlertDialog dd = new AlertDialog.Builder(this)
                .setTitle("Report")
                .setIcon(R.drawable.logotwo)
                .setCancelable(false)
                .setView(R.layout.dialogefeedbacklayout_report)
                .create();
        dd.show();


        Button acceptt= (Button) dd.findViewById(R.id.btn_dec);


        acceptt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ClientShowActivity.this, "REPORT DONE", Toast.LENGTH_LONG).show();
                dd.hide();
                onBackPressed();

            }
        });




    }
}
