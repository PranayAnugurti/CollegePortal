package com.praneethcorporation.collegeportal;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.praneethcorporation.collegeportal.Adapters.CompaniesAdapter;
import com.praneethcorporation.collegeportal.InfoClasses.Company;
import com.praneethcorporation.collegeportal.InfoClasses.User;
import com.praneethcorporation.collegeportal.PlaceMentStatisticsPackage.PlaceMentStatistics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 8/31/2017.
 */

public class RegisterCompanies extends AppCompatActivity {


    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    CompaniesAdapter adapter;
    String json_url;
    String reg_no;

    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeredcompanies);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        ArrayList<Company> companyArrayList = new ArrayList<>();

        adapter = new CompaniesAdapter(this, companyArrayList);
        new CompanyBackgroundTask(this).execute();
        reg_no = "20155017";
        ListView listView = (ListView) findViewById(R.id.registeredCompaniesList);
        listView.setAdapter(adapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registered Companies");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                } else if (id == R.id.nav_currentOpening) {
                    Intent intent = new Intent(getApplicationContext(), CurrentOpenings.class);
                    startActivity(intent);
                } else if (id == R.id.nav_registeredCompanies) {
                    Intent intent = new Intent(getApplicationContext(), RegisterCompanies.class);
                    startActivity(intent);
                } else if (id == R.id.nav_addInterviewExperience) {
                    Intent intent = new Intent(getApplicationContext(), AddInterviewExperience.class);
                    startActivity(intent);
                } else if (id == R.id.nav_currentOpening) {
                    Intent intent = new Intent(getApplicationContext(), CurrentOpenings.class);
                    startActivity(intent);
                } else if (id == R.id.nav_registeredCompanies) {
                    Intent intent = new Intent(getApplicationContext(), RegisterCompanies.class);
                    startActivity(intent);
                } else if (id == R.id.nav_interviewExperinces) {
                    Intent intent = new Intent(getApplicationContext(), InterviewExperinces.class);
                    startActivity(intent);

                } else if (id == R.id.nav_place_stats) {
                    Intent intent = new Intent(getApplicationContext(), PlaceMentStatistics.class);
                    startActivity(intent);
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logOut) {
            User user = new User(getApplicationContext());
            user.remove();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.aboutdevelopers) {
            Intent intent = new Intent(getApplicationContext(), AboutDevelopers.class);
            startActivity(intent);

        }
        if (id == R.id.help) {
            Intent intent = new Intent(getApplicationContext(), Help.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }


    public class CompanyBackgroundTask extends AsyncTask<String, Void, String> {

        String json_url;
        Context ctx;

        CompanyBackgroundTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            json_url = "http://139.59.5.186/php/registered_companies.php";
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                //Log.d("O_MY", String.valueOf(httpURLConnection.getResponseCode()));
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream, "UTF-8"));
                String data =
                        URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(reg_no, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();

                String JSON_STRING;
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            ArrayList<Company> companies = new ArrayList<>();
            try {

                Log.d("O_MY", "Result=" + result);
                JSONObject base_json = new JSONObject(result);
                Log.d("O_MY", base_json.toString());
                JSONArray jsonArray = base_json.getJSONArray("response");
                int i = 0;
                Log.d("O_MY", "length=" + jsonArray.length());
                while (i < jsonArray.length()) {
                    Log.d("O_MY", "pos=" + i);
                    JSONObject company = jsonArray.getJSONObject(i);
                    Log.d("O_MY", "company=" + company.getString("company"));

                    Company Currentcompany = new Company(
                            company.getString("company"),
                            company.getString("profile"),
                            company.getString("location"),
                            company.getString("ctc"),
                            company.getString("dateReg")
                    );

                    companies.add(i, Currentcompany);
                    i++;
                }
                adapter.addAll(companies);


            } catch (JSONException e) {
                e.printStackTrace();
            }
progressBar.setVisibility(View.INVISIBLE);

        }
    }
}
