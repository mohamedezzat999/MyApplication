package com.example.macbookair.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ProfileWorkerActivity extends AppCompatActivity {

    TextView workerName, workerPhone, workerJob, workerCost, workerBio, workerExperience, workerGender, workerAddress ;
    RatingBar workerRate;
    String newString ;
    private EditText text_passs ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_worker);
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

        Button edit = (Button) findViewById(R.id.EditWorkerProfile)  ;
        Button req = (Button) findViewById(R.id.RequestWorkerProfile)  ;
        workerName = (TextView) findViewById(R.id.WorkerName);
        workerJob = (TextView)findViewById(R.id.jop);
        workerCost = (TextView)findViewById(R.id.WorkerCost);
        workerBio = (TextView)findViewById(R.id.WorkerBio);
        workerExperience =(TextView) findViewById(R.id.WorkerExperience);
        workerAddress = (TextView)findViewById(R.id.WorkerAddress);
        workerPhone = (TextView)findViewById(R.id.WorkerPhone);
        workerGender = (TextView)findViewById(R.id.WorkerGender);

        workerRate =(RatingBar) findViewById(R.id.rating);
        workerRate.setRating(3);

        getJSONProduct("http://134.209.187.147/api/user");
       // getJSONJop("http://134.209.187.147/api/categories");


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog();
            }
        });
        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext() , RequestsActivity.class);
                i.putExtra("STRING_I_NEED" , newString);
                startActivity(i);
            }
        });
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
                    JSONObject details = jObj.getJSONObject("data");

                    int b = details.getInt("job");

                    //Toast.makeText(ProfileWorkerActivity.this, b, Toast.LENGTH_SHORT).show();

                    workerName.setText(details.getString("name"));
                    workerPhone.setText(details.getString("phone"));
                    workerJob.setText(String.valueOf(details.getInt("job")));
                    workerCost.setText("200 LE"); //
                    workerBio.setText(details.getString("name")); //
                    workerExperience.setText("EXP"); //
                    workerGender.setText("Male"); //
                    workerAddress.setText("Hadayek"); //


                    getJSONJop("http://134.209.187.147/api/categories" , b);


                } catch (JSONException e) {
                    Toast.makeText(ProfileWorkerActivity.this, "erreor" + e.toString(), Toast.LENGTH_SHORT).show();
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
                    con.setRequestProperty ("Accept", "application/json");
                    con.setRequestProperty ("Authorization", "Bearer "+newString);

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
    private void getJSONJop(final String urlWebService , final int x_id) {

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
                    JSONArray details = jObj.getJSONArray("data");

                    for (int i=0; i < details.length(); i++) {
                        JSONObject element = details.getJSONObject(i) ;
                        int id_test = element.getInt("id") ;
                        if (id_test == x_id)
                        {
                            workerJob.setText(element.getString("name"));
                            break ;
                        }

                    }





                } catch (JSONException e) {
                    Toast.makeText(ProfileWorkerActivity.this, "erreor" + e.toString(), Toast.LENGTH_SHORT).show();
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
                    con.setRequestProperty ("Accept", "application/json");
                    con.setRequestProperty ("Authorization", "Bearer "+newString);

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
    private void check(String pass) throws JSONException {


        //RequestQueue myrequestqueue = Volley.newRequestQueue(this);
        String url = "http://134.209.187.147/api/check?password="+pass;

        //String url = "http://httpbin.org/post";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = new JSONObject("{\"message\": \"Hello\"}");

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                String status = " " ;
                try {
                    status  = response.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status.equals(" ")) {
                    Toast.makeText(ProfileWorkerActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (status.equals("true")) {
                        Toast.makeText(ProfileWorkerActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext() , EditWorkerProfileActivity.class);
                        i.putExtra("STRING_I_NEED" , newString);
                        startActivity(i);
                    }else {
                        Toast.makeText(ProfileWorkerActivity.this, "Password Incorrect", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(ProfileWorkerActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer "+newString);


                return params;
            }
        };

// Add the request to the queue
        Volley.newRequestQueue(this).add(jsonRequest);
    }
    private void displayDialog()
    {

        final AlertDialog d = new AlertDialog.Builder(this)
                .setTitle("Check Password")
                .setIcon(R.drawable.logotwo)
                .setView(R.layout.dialogefeedbacklayout_check)
                .create();
        d.show();


        text_passs = (EditText) d.findViewById(R.id.text_pass) ;
        Button check_btn = (Button) d.findViewById(R.id.btn_dec) ;


        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (! text_passs.getText().toString().equals("")) {
                    try {
                        check(text_passs.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(ProfileWorkerActivity.this, "D5l el bynat", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

}
