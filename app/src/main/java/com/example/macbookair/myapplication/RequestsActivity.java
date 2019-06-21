package com.example.macbookair.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestsActivity extends AppCompatActivity {

    private ListView list ;
    private ArrayList<String> itemname = new ArrayList<>() ;
    private ArrayList<String> itembusy = new ArrayList<>() ;
    private ArrayList<String> itemkey = new ArrayList<>() ;
    private String newString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        getSupportActionBar().setTitle("Requests");
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
        list = (ListView) findViewById(R.id.list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                String selecteditem = itemname.get(position);


                        ArrayList<String>items = new ArrayList<>() ;
                        items.add("Accept") ;
                        items.add("Decline") ;
                        items.add("End Work") ;

                        new MaterialDialog.Builder(RequestsActivity.this)
                                .title("Options")
                                .items(items)
                                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {


                                        if (text == null) {
                                            Toast.makeText(RequestsActivity.this, "You dont choose options", Toast.LENGTH_SHORT).show();
                                        }else {
                                            if (text.equals("End Work")) {
                                                try {
                                                    getRegister("http://134.209.187.147/api/update-order?order_id=" + itemkey.get(position) + "&is_done=1");
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            else if (text.equals("Decline")) {
                                                try {
                                                    getRegister("http://134.209.187.147/api/update-order?order_id=" + itemkey.get(position) + "&is_done=1");
                                                    displayDialogTwo();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            else if (text.equals("Accept")) {
                                                Toast.makeText(RequestsActivity.this, "Accepted", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext() , ClientShowActivity.class));
                                            } else {
                                            }
                                        }

                                        return true;

                                    }
                                })
                                .positiveText("Choose")
                                .buttonRippleColorRes(R.color.colorPrimaryTwo)
                                .backgroundColorRes(R.color.colorPrimaryTwo)
                                .show();



            }
        });

        getJSONProduct("http://134.209.187.147/api/user");

       // getJSONdone("http://134.209.187.147/api/update-order?order_id=5&is_done=1");

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
                    //JSONObject details = jObj.getJSONObject("data");

                    JSONObject detailss = jObj.getJSONObject("data");
                    JSONArray details = detailss.getJSONArray("worker_orders");


                    for (int i=0; i < details.length(); i++) {

                        JSONObject element = details.getJSONObject(i) ;
                        String x = element.getString("created_at") ;
                        itemname.add(x);
                        if (element.getInt("is_done") == 0){
                            itembusy.add("green");
                        }
                        if (element.getInt("is_done") == 1){
                            itembusy.add("red");
                        }
                        itemkey.add(String.valueOf(element.getInt("id"))) ;
                        //Toast.makeText(RequestsActivity.this, element.getString("id"), Toast.LENGTH_SHORT).show();

                    }
                    CustomListAdapterThree adapter = new CustomListAdapterThree(RequestsActivity.this, itemname , itembusy);
                    list.setAdapter(adapter);


                } catch (JSONException e) {
                    Toast.makeText(RequestsActivity.this, "error - " + e.toString(), Toast.LENGTH_LONG).show();
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

    private void getJSONdone(final String urlWebService) {

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
                    String details = jObj.getString("message");
                    Toast.makeText(RequestsActivity.this, details, Toast.LENGTH_SHORT).show();



                } catch (JSONException e) {
                    Toast.makeText(RequestsActivity.this, "error - " + e.toString(), Toast.LENGTH_LONG).show();
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

    private void getRegister(String url) throws JSONException {



        JSONObject jsonBody = new JSONObject("{\"message\": \"Hello\"}");

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                //Toast.makeText(RequestsActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(RequestsActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("Accept", "application/json");
                params.put("Authorization", "Bearer "+newString);


                return params;
            }
        };

// Add the request to the queue
        Volley.newRequestQueue(this).add(jsonRequest);
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

                Toast.makeText(RequestsActivity.this, "REPORT DONE", Toast.LENGTH_LONG).show();
                dd.hide();
                Intent i = new Intent(getApplicationContext(), RequestsActivity.class);
                i.putExtra("STRING_I_NEED", newString);
                startActivity(i);

            }
        });




    }


}
