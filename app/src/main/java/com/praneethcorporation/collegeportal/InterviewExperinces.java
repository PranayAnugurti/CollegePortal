package com.praneethcorporation.collegeportal;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.praneethcorporation.collegeportal.Adapters.InterviewExperiencesAdapter;
import com.praneethcorporation.collegeportal.InfoClasses.InterviewExperiencesInfo;
import com.praneethcorporation.collegeportal.PlaceMentStatisticsPackage.PlaceMentStatistics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InterviewExperinces extends AppCompatActivity {

  Toolbar toolbar;
  DrawerLayout drawerLayout;
  ActionBarDrawerToggle actionBarDrawerToggle;
  NavigationView navigationView;


  ListView listView;
    InterviewExperiencesAdapter interviewExperiencesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_experinces);

listView = (ListView)findViewById(R.id.interviewExperiencesList);

      toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      getSupportActionBar().setTitle("Add InterView Experience");
      drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
      actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
      drawerLayout.addDrawerListener(actionBarDrawerToggle);
      navigationView = (NavigationView) findViewById(R.id.navigationView);
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


      ArrayList<InterviewExperiencesInfo> arrayList = new ArrayList<>();
        interviewExperiencesAdapter = new InterviewExperiencesAdapter(getApplicationContext(),arrayList);

        listView.setAdapter(interviewExperiencesAdapter);
      InterviewBackgroundTask task=new InterviewBackgroundTask(this);
      task.execute();

    }


  @Override
  public void onPostCreate(@Nullable Bundle savedInstanceState) {

    super.onPostCreate(savedInstanceState);
    actionBarDrawerToggle.syncState();
  }


  public class InterviewBackgroundTask extends AsyncTask<String, Void, String> {

    String json_url;
    Context ctx;

    InterviewBackgroundTask(Context ctx) {
      this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
      json_url = "http://139.59.5.186/php/interview.php";
      Log.d("O_MY",UserInfo.reg_no);
    }

    @Override
    protected String doInBackground(String... params) {

      try {
        URL url = new URL(json_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        //Log.d("O_MY", String.valueOf(httpURLConnection.getResponseCode()));
        httpURLConnection.setDoOutput(false);
        httpURLConnection.setDoInput(true);
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
      ArrayList<InterviewExperiencesInfo> interviews = new ArrayList<>();
      try {

        Log.d("O_MY", "Result=" + result);

        JSONObject base_json = new JSONObject(result);
        Log.d("O_MY", base_json.toString());

        JSONArray jsonArray = base_json.getJSONArray("response");
        int i = 0;
        Log.d("O_MY", "length=" + jsonArray.length());
        while (i < jsonArray.length()) {
          Log.d("O_MY", "pos=" + i);
          JSONObject interview = jsonArray.getJSONObject(i);
          
          InterviewExperiencesInfo interviewItem = new InterviewExperiencesInfo(
              interview.getString("reg_no"),
              interview.getString("company"),
              interview.getString("name"),
              interview.getString("date"),
              interview.getString("experience")
          );
          interviews.add(i, interviewItem);

          i++;
        }
        interviewExperiencesAdapter.addAll(interviews);


      } catch (JSONException e) {
        e.printStackTrace();
      }


    }
  }

}
