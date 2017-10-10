package com.praneethcorporation.collegeportal;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.praneethcorporation.collegeportal.Registration.BackgroundTask;
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
 *
 * Created by user on 8/31/2017.
 */

public class CurrentOpenings extends AppCompatActivity {

    ListView listView;
  CurrentOpeningsAdapter currentOpeningsAdapter;

  @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.currentopenings);
        listView = (ListView) findViewById(R.id.currentOpeningList);

        ArrayList<CurrentOpeningCompanies> arrayList = new ArrayList<>();

        currentOpeningsAdapter = new CurrentOpeningsAdapter(this, arrayList);

        listView.setAdapter(currentOpeningsAdapter);
        BackgroundTask task=new BackgroundTask(this);
        task.execute();
    }
  public class BackgroundTask extends AsyncTask<String,Void,String> {

    String json_url;
    Context ctx;

    BackgroundTask(Context ctx){
      this.ctx=ctx;
    }

    @Override
    protected void onPreExecute() {
      json_url = "http://139.59.5.186/php/current_openings.php";
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
      ArrayList<CurrentOpeningCompanies> openings = new ArrayList<>();
      try {

        Log.d("O_MY", "Result="+result);
        JSONObject base_json = new JSONObject(result);
        Log.d("O_MY", base_json.toString());
        JSONArray jsonArray = base_json.getJSONArray("response");
        int i = 0;
        Log.d("O_MY","length="+jsonArray.length());
        while (i<jsonArray.length()) {
          Log.d("O_MY","pos="+i);
          JSONObject current = jsonArray.getJSONObject(i);


          CurrentOpeningCompanies Currentopening = new CurrentOpeningCompanies(
              current.getString("company"),
              current.getString("profile"),
              current.getString("ctc"),
              current.getString("branches"),
              current.getString("examdate"),
              current.getString("isRegistered"),
              current.getString("location")
          );

          openings.add(i,Currentopening);
          i++;
        }
        currentOpeningsAdapter.addAll(openings);


      } catch (JSONException e) {
        e.printStackTrace();
      }


    }
  }
}
