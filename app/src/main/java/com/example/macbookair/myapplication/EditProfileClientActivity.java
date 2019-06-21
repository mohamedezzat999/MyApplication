package com.example.macbookair.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class EditProfileClientActivity extends AppCompatActivity {

    private EditText name , address , telephone , password ;
    private String newString ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_client);
        getSupportActionBar().setTitle("Edit Profile");

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

        name = (EditText) findViewById(R.id.WorkerNameEdit) ;
        address = (EditText) findViewById(R.id.WorkerAddressEdit) ;
        telephone = (EditText) findViewById(R.id.WorkerPhoneEdit) ;
        password = (EditText) findViewById(R.id.WorkerPasswordEdit) ;
        Button supmit =  (Button) findViewById(R.id.SubmitWorkerEdit) ;


        supmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getRegister();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(getApplicationContext() , ClientProfileActivity.class);
                i.putExtra("STRING_I_NEED" , newString);
                startActivity(i);

            }
        });


        getJSONProduct("http://134.209.187.147/api/user");

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

                    //Toast.makeText(ProfileWorkerActivity.this, b, Toast.LENGTH_SHORT).show();

                    name.setText(details.getString("name"));
                    telephone.setText(details.getString("phone"));
                    //workerGender.setText("Male"); //
                    address.setText("Haram"); //




                } catch (JSONException e) {
                    Toast.makeText(EditProfileClientActivity.this, "erreor" + e.toString(), Toast.LENGTH_SHORT).show();
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

    private void getRegister() throws JSONException {

        // 01144681123
        // mostafa ahmed
        // 26


        //RequestQueue myrequestqueue = Volley.newRequestQueue(this);
        String url = "http://134.209.187.147/api/edit-profile?name="+name.getText().toString()+"&password="+password.getText().toString()+
                "&phone="+telephone.getText().toString();

        //String url = "http://httpbin.org/post";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = new JSONObject("{\"message\": \"Hello\"}");

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                Toast.makeText(EditProfileClientActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(EditProfileClientActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
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


}
