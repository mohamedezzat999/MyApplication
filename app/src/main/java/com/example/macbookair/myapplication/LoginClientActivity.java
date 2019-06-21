package com.example.macbookair.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginClientActivity extends AppCompatActivity {

    private SlidingUpPanelLayout mLayout;
    private TextView forget;
    private Button login;
    private EditText email, pass;
    private Boolean result;
    private String newString ;

    RadioButton one, two ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_client);



        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        forget = (TextView) findViewById(R.id.forget);
        login = (Button) findViewById(R.id.btn_login);


        email = (EditText) findViewById(R.id.input_email);
        pass = (EditText) findViewById(R.id.input_password);

        TextView terms  = (TextView) findViewById(R.id.terms) ;
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , NewTermsActivity.class));
            }
        });



        one = (RadioButton)findViewById(R.id.rbmale);
        two = (RadioButton)findViewById(R.id.rbfemale);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getJSONProduct("http://134.209.187.147/api/login?username=gm.xerk@gmail.com&password=35604578");
                //startActivity(new Intent(getApplicationContext() , SelectServiceClientActivity.class));
                //getJSONProduct("http://134.209.187.147/api/login?username=gm.xerk@gmail.com&password=35604578");

                //email.setText("gm.xerk@gmail.com");
                //pass.setText("35604578");
                if (email.getText().toString().equals("") || pass.getText().toString().equals("")) {
                    Toast.makeText(LoginClientActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }else {
                    getLogin(email.getText().toString() , pass.getText().toString());
                }



            }
        });

        panelListener();

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
            }
        });

        Button register = (Button) findViewById(R.id.button3) ;
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRegister();
            }
        });

    }


    public void panelListener() {

        mLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {

            // During the transition of expand and collapse onPanelSlide function will be called.
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            // Called when secondary layout is dragged up by user
            @Override
            public void onPanelExpanded(View panel) {

            }

            // Called when secondary layout is dragged down by user
            @Override
            public void onPanelCollapsed(View panel) {

            }

            @Override
            public void onPanelAnchored(View panel) {

            }

            @Override
            public void onPanelHidden(View panel) {

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
                    Toast.makeText(LoginClientActivity.this, "Here", Toast.LENGTH_SHORT).show();
                    jObj = new JSONObject(s);
                    //JSONObject details = jObj.getJSONObject("status");
                     //String status  = details.toString() ;
                     //Toast.makeText(LoginClientActivity.this, status, Toast.LENGTH_SHORT).show();
                    // // Toast.makeText(LoginClientActivity.this, details, Toast.LENGTH_SHORT).show();

                    Intent intent = getIntent();
                    //String nameCat = intent.getStringExtra("nameCatthree");

                    /*for (int i=0; i < details.length(); i++) {

                        JSONObject element = details.getJSONObject(i) ;
                        String x = element.getString("id") ;

                        if ( x.equals(nameCat))
                        {
                            //Toast.makeText(ProductActivity.this, "Done", Toast.LENGTH_SHORT).show();
                            //String name = element.getString("name") ;



                            break;

                        }


                    }*/


                } catch (JSONException e) {
                    Toast.makeText(LoginClientActivity.this, "erreor" + e.toString(), Toast.LENGTH_SHORT).show();
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


    private void getLogin (String name , String password) {
        RequestQueue myrequestqueue = Volley.newRequestQueue(this);
        String url = "http://134.209.187.147/api/login?username="+name+"&password="+password;
        final StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                JSONObject data_js = null ;
                try {
                    data_js = new JSONObject(response);
                    String status = data_js.getString("status");
                    JSONObject data = data_js.getJSONObject("login") ;
                    String token = data.getString("access_token");
                    //Toast.makeText(LoginClientActivity.this, token , Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginClientActivity.this, SelectServiceClientActivity.class);
                    i.putExtra("STRING_I_NEED", token);
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginClientActivity.this, "YOUR PHONE OR PASSWORD IS INCORRENT", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("Field", "Value"); //Add the data you'd like to send to the server.
                return MyData;
            }
        };
        myrequestqueue.add(myStringRequest);
    }

    private void getRegister(){

        EditText name , phone , email , password ;
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.mobile);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);



        RequestQueue myrequestqueue = Volley.newRequestQueue(this);
        String url = "http://134.209.187.147/api/register?name="+name.getText().toString()+"&email="+email.getText().toString()+"&password="+password.getText().toString()+"&job=0&phone="+phone.getText().toString()+"&age=20";

        final StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                JSONObject data_js = null ;
                try {
                    data_js = new JSONObject(response);
                    String status = data_js.getString("success_message");
                    Toast.makeText(LoginClientActivity.this, status, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    Toast.makeText(LoginClientActivity.this, "THIS NUMBER ALREADY HAS AN ACCOUNT"+"- ", Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginClientActivity.this, "YOUR DATA IS INCORRECT" + " - " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("Field", "Value"); //Add the data you'd like to send to the server.
                return MyData;
            }
        };
        myrequestqueue.add(myStringRequest);
    }



    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String str="";
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbmale:
                if(checked)
                    str = "Male";
                break;
            case R.id.rbfemale:
                if(checked)
                    str = "FeMale";
                break;

        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

}
