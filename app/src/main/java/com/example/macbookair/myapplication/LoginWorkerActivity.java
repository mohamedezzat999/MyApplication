package com.example.macbookair.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginWorkerActivity extends AppCompatActivity {


    private SlidingUpPanelLayout mLayout;
    private TextView forget ;
    private Button login ;
    private Spinner spinner_one , spinner_two , spinner_three , spinner_four ;
    private EditText email, pass;
    RadioButton one, two ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getAttributes().windowAnimations = R.style.Fade;

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        forget = (TextView) findViewById(R.id.forget);
        login = (Button) findViewById(R.id.btn_login);

        spinner_one = (Spinner) findViewById(R.id.spinner) ;
        spinner_two = (Spinner) findViewById(R.id.spinner2) ;
        spinner_three = (Spinner) findViewById(R.id.spinner3) ;
        spinner_four = (Spinner) findViewById(R.id.spinner4) ;

        one = (RadioButton)findViewById(R.id.rbmale);
        two = (RadioButton)findViewById(R.id.rbfemale);

        email = (EditText) findViewById(R.id.input_email);
        pass = (EditText) findViewById(R.id.input_password);


        TextView terms  = (TextView) findViewById(R.id.terms) ;
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , NewTermsActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getJSONProduct("http://134.209.187.147/api/login?username=gm.xerk@gmail.com&password=35604578");
                //startActivity(new Intent(getApplicationContext() , SelectServiceClientActivity.class));
                //getJSONProduct("http://134.209.187.147/api/login?username=gm.xerk@gmail.com&password=35604578");

                email.setText("0000000");
                pass.setText("0000000");
                if (email.getText().toString().equals("") || pass.getText().toString().equals("")) {
                    Toast.makeText(LoginWorkerActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }else {
                    getLogin(email.getText().toString() , pass.getText().toString());
                }



            }
        });

        panelListener();

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , ForgetPasswordActivity.class));
            }
        });

        addItemsOnSpinner();
        addItemsOnSpinner2();
        addItemsOnSpinner3();
        addItemsOnSpinner4();


        Button register = (Button) findViewById(R.id.button3) ;
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRegister();
            }
        });

    }
    public void addItemsOnSpinner() {



        final List<String> list = new ArrayList<String>();
        list.add("JOP");
        list.add("Babysitter");
        list.add("Winch");
        list.add("Plumber");
        list.add("Carpenter");
        list.add("Electrician");
        list.add("Maid");
        list.add("Eldercare");
        list.add("Accident Emergency");



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_one.setAdapter(dataAdapter);
        spinner_one.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
    public void addItemsOnSpinner2() {



        List<String> list = new ArrayList<String>();
        list.add("Proffesionality Level");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_two.setAdapter(dataAdapter);
    }
    public void addItemsOnSpinner3() {



        List<String> list = new ArrayList<String>();
        list.add("City");
        list.add("Cairo");




        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_three.setAdapter(dataAdapter);
    }
    public void addItemsOnSpinner4() {



        List<String> list = new ArrayList<String>();
        list.add("Region");
        list.add("Haram");
        list.add("Fesal");
        list.add("Maryotya");
        list.add("Hadba");
        list.add("6 oct");
        list.add("Imbaba");



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_four.setAdapter(dataAdapter);
    }


    public void panelListener(){

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
                    Intent i = new Intent(LoginWorkerActivity.this, ProfileWorkerActivity.class);
                    i.putExtra("STRING_I_NEED", token);
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginWorkerActivity.this, "YOUR PHONE OR PASSWORD IS INCORRENT", Toast.LENGTH_SHORT).show();
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
        String jop = spinner_one.getSelectedItem().toString();
        String city = spinner_three.getSelectedItem().toString();
        String region = spinner_four.getSelectedItem().toString();


        RequestQueue myrequestqueue = Volley.newRequestQueue(this);
        String url = "http://134.209.187.147/api/register?name="+name.getText().toString()+"&email="+email.getText().toString()+"&password="+password.getText().toString()+"&job=1&phone="+phone.getText().toString()+"&age=20&price=200&city_id=1&region_id=1&category_id=1&identifier=123456789123451";

        final StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                JSONObject data_js = null ;
                try {
                    data_js = new JSONObject(response);
                    String status = data_js.getString("success_message");
                    Toast.makeText(LoginWorkerActivity.this, status, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginWorkerActivity.this, "YOUR DATA IS INCORRECT", Toast.LENGTH_SHORT).show();
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

}
