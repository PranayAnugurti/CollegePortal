package com.praneethcorporation.collegeportal;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

public class InterviewExperinces extends AppCompatActivity {

    ListView listView;
    InterviewExperiencesAdapter interviewExperiencesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_experinces);

listView = (ListView)findViewById(R.id.interviewExperiencesList);

        ArrayList<InterviewExperiencesInfo> arrayList = new ArrayList<>();
        interviewExperiencesAdapter = new InterviewExperiencesAdapter(getApplicationContext(),arrayList);

        listView.setAdapter(interviewExperiencesAdapter);
      InterviewBackgroundTask task=new InterviewBackgroundTask(this);
      task.execute();

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
