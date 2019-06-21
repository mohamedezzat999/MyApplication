package com.example.macbookair.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WorkerShowActivity extends AppCompatActivity {
    TextView workerName, workerPhone, workerJob, workerCost, workerBio, workerExperience, workerGender, workerAddress ;
    RatingBar workerRate;
    private String newString ;
    Button call_btn , cancel ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_show);
        getSupportActionBar().setTitle("Worker Profile");



        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("STRING_I_NEED");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }


        workerName = (TextView) findViewById(R.id.WorkerName);
        workerJob = (TextView)findViewById(R.id.jop);
        workerCost = (TextView)findViewById(R.id.WorkerCost);
        workerBio = (TextView)findViewById(R.id.WorkerBio);
        workerExperience =(TextView) findViewById(R.id.WorkerExperience);
        workerAddress = (TextView)findViewById(R.id.WorkerAddress);
        workerPhone = (TextView)findViewById(R.id.WorkerPhone);
        workerGender = (TextView)findViewById(R.id.WorkerGender);
        call_btn = (Button) findViewById(R.id.call_btn);
        cancel = (Button) findViewById(R.id.cancel) ;


        workerRate =(RatingBar) findViewById(R.id.rating);
        workerRate.setRating(3);

        call_btn.setOnClickListener(new View.OnClickListener() {
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
        getJSONProduct("http://134.209.187.147/api/workers?per_page=100&category=1&region_id=1");


    }
    private void getJSONProduct(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                JSONObject jObj = null;
                try {

                    jObj = new JSONObject(s);
                    JSONObject detailss = jObj.getJSONObject("data");
                    JSONArray details = detailss.getJSONArray("data");

                    for (int i=0; i < details.length(); i++) {

                        JSONObject element = details.getJSONObject(i) ;
                        String x = element.getString("name") ;
                        if (element.getInt("id") == 40) {
                            workerName.setText(element.getString("name"));
                            workerPhone.setText(element.getString("phone"));
                            workerJob.setText("Carpenter");
                            workerCost.setText("200 LE"); //
                            workerExperience.setText("EXP"); //
                            workerGender.setText("Male"); //
                            workerAddress.setText("Hadayek"); //
                        }

                    }





                } catch (JSONException e) {
                    Toast.makeText(WorkerShowActivity.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }

            @Override
            protected String doInBackground(Void... voids) {


                try {

                    //creating a URL
                    URL url = new URL(urlWebService);

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    //con.setRequestProperty ("Accept", "application/json");
                    //con.setRequestProperty ("Authorization", "Bearer "+newString);

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;

                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {

                        //appending it to string builder
                        sb.append(json + "\n");
                    }

                    //finally returning the read string
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
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

                Toast.makeText(WorkerShowActivity.this, "REPORT DONE", Toast.LENGTH_LONG).show();
                dd.hide();
                onBackPressed();

            }
        });




    }
}
