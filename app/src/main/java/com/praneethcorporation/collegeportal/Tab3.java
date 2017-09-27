package com.praneethcorporation.collegeportal;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
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
 * Created by user on 8/29/2017.
 */

public class Tab3 extends Fragment {

  InternshipAdapter adapter;
  ProjectAdapter projectAdapter;

  String reg_no;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.tab3, container, false);
    Context c = getActivity().getApplicationContext();

    // Construct the data source
    ArrayList<Internship> arrayOfInternships = new ArrayList<Internship>();
    ArrayList<Project> arrayOfProjects = new ArrayList<Project>();
    Intent i = getActivity().getIntent();
    reg_no = i.getStringExtra("reg_no");

    adapter = new InternshipAdapter(view.getContext(), arrayOfInternships);
    projectAdapter=new ProjectAdapter(view.getContext(),arrayOfProjects);
    new InternBackgroundTask(c).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    new ProjectBackgroundTask(c).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    ExpandableHeightListView listView = (ExpandableHeightListView) view
        .findViewById(R.id.intenshipsList);
    listView.setAdapter(adapter);
    listView.setExpanded(true);

    ExpandableHeightListView projectListView = (ExpandableHeightListView) view
        .findViewById(R.id.projectsList);
    projectListView.setAdapter(projectAdapter);
    projectListView.setExpanded(true);

    return view;
  }

  public class InternBackgroundTask extends AsyncTask<String, Void, String> {

    String json_url;
    Context ctx;

    InternBackgroundTask(Context ctx) {
      this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
      json_url = "http://139.59.5.186/php/internship_info.php";
      Log.d("O_MY", reg_no);
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
      ArrayList<Internship> internships = new ArrayList<>();
      try {

        Log.d("O_MY", "Result="+result);

        JSONObject base_json = new JSONObject(result);
        Log.d("O_MY", base_json.toString());

        JSONArray jsonArray = base_json.getJSONArray("response");
        int i = 0;
        Log.d("O_MY","length="+jsonArray.length());
        while (i<jsonArray.length()) {
          Log.d("O_MY","pos="+i);
          JSONObject intern = jsonArray.getJSONObject(i);
          Log.d("O_MY","intern="+intern.getString("internship"));

          Internship internship = new Internship(
              intern.getString("reg_no"),
              intern.getString("internship"),
              intern.getString("company"),
              intern.getString("duration"),
              intern.getString("description")
          );
          Log.d("O_MY",intern.getString("internship")+intern.getString("company"));
          internships.add(i,internship);

          i++;
        }
        adapter.addAll(internships);


      } catch (JSONException e) {
        e.printStackTrace();
      }


    }
  }
  public class ProjectBackgroundTask extends AsyncTask<String,Void,String> {

    String json_url;
    Context ctx;

    ProjectBackgroundTask(Context ctx) {
      this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
      json_url = "http://139.59.5.186/php/project_info.php";
      Log.d("O_MY", reg_no);
    }

    @Override
    protected String doInBackground(String... params) {

      try {
        URL url = new URL(json_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        //Log.d("O_MY", "Projects httpResponseCode="+String.valueOf(httpURLConnection.getResponseCode()));
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
      ArrayList<Project> projects = new ArrayList<>();
      try {

        Log.d("O_MY", "Result="+result);

        JSONObject base_json = new JSONObject(result);
        Log.d("O_MY", "projects="+base_json.toString());

        JSONArray jsonArray = base_json.getJSONArray("response");
        int i = 0;
        Log.d("O_MY","length="+jsonArray.length());
        while (i<jsonArray.length()) {
          
          JSONObject projectItem = jsonArray.getJSONObject(i);
         

          Project Project = new Project(
              projectItem.getString("reg_no"),
              projectItem.getString("project"),
              projectItem.getString("duration"),
              projectItem.getString("description"),
              projectItem.getString("project_link")
          );
          projects.add(i,Project);

          i++;
        }
        projectAdapter.addAll(projects);


      } catch (JSONException e) {
        e.printStackTrace();
      }


    }
  }
}
