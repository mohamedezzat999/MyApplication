package com.example.macbookair.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectServiceClientActivity extends AppCompatActivity {

    private ListView list ;
    private ArrayList<String> itemname = new ArrayList<>() ;
    private ArrayList<String> items = new ArrayList<>() ;
    private ArrayList<Integer> itemimg = new ArrayList<>();
    private TextView choose_txt ;
    private Toolbar toolbar ;
    private DrawerLayout drawerLayout;
    private String newString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service_client);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
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

        setContentView(R.layout.navigation_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select Your Service");
        initNavigationDrawer();

        getJSONProduct("http://134.209.187.147/api/categories");


        list = (ListView) findViewById(R.id.list);
        choose_txt = (TextView) findViewById(R.id.choose);




        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                String selecteditem = itemname.get(position);

                Intent i = new Intent(SelectServiceClientActivity.this, SelectWorkerActivity.class);
                i.putExtra("STRING_I_NEED", newString);
                startActivity(i);

            }
        });



        choose_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items = new ArrayList<>() ;
                items.add("Giza") ;
                items.add("Cairo") ;

                new MaterialDialog.Builder(SelectServiceClientActivity.this)
                        .title("Choose Area")
                        .items(items)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                choose_txt.setText(text);

                                return true;
                            }
                        })
                        .positiveText("Select")
                        .backgroundColorRes(R.color.colorPrimary)
                        .show();
            }
        });

    }

    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.notifications:
                        Intent i = new Intent(SelectServiceClientActivity.this, ClientProfileActivity.class);
                        i.putExtra("STRING_I_NEED", newString);
                        startActivity(i);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.sessions:
                        //    startActivity(new Intent(getApplicationContext() , SubjectsActivity.class));
                        //throw
                        break;
                    case R.id.contact:
                        //startActivity(new Intent(ProfileActivity.this , ContactActivity.class));
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        finish();

                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView tv_email = (TextView)header.findViewById(R.id.tv_email);
        CircleImageView circleImageView = (CircleImageView) header.findViewById(R.id.profile_imagee) ;
        //tv_email.setText("Dr Ahmed Nabih");
        //circleImageView.setImageResource(R.drawable.drnabih);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectServiceClientActivity.this, ClientProfileActivity.class);
                i.putExtra("STRING_I_NEED", newString);
                startActivity(i);
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
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
                    JSONArray details = jObj.getJSONArray("data");


                    for (int i=0; i < details.length(); i++) {

                        JSONObject element = details.getJSONObject(i) ;
                        String x = element.getString("name") ;
                        itemname.add(x);
                        itemimg.add(R.drawable.logotwo) ;


                    }
                    CustomListAdapter adapter = new CustomListAdapter(SelectServiceClientActivity.this, itemname , itemimg);
                    list.setAdapter(adapter);


                } catch (JSONException e) {
                    Toast.makeText(SelectServiceClientActivity.this, "erreor" + e.toString(), Toast.LENGTH_SHORT).show();
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





}
