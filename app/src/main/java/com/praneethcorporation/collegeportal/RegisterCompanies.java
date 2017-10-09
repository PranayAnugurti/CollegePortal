package com.praneethcorporation.collegeportal;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.praneethcorporation.collegeportal.Adapters.CompaniesAdapter;
import com.praneethcorporation.collegeportal.InfoClasses.Company;

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

  CompaniesAdapter adapter;
  String json_url;
  String reg_no;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.registeredcompanies);

      ArrayList<Company> companyArrayList=new ArrayList<>();

      adapter=new CompaniesAdapter(this,companyArrayList);
      new CompanyBackgroundTask(this).execute();
      reg_no="20155017";
      ListView listView=(ListView)findViewById(R.id.registeredCompaniesList);
      listView.setAdapter(adapter);

    }
    public class CompanyBackgroundTask extends AsyncTask<String,Void,String>{

      String json_url;
      Context ctx;

      CompanyBackgroundTask(Context ctx){
        this.ctx=ctx;
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

          Log.d("O_MY", "Result="+result);
          JSONObject base_json = new JSONObject(result);
          Log.d("O_MY", base_json.toString());
          JSONArray jsonArray = base_json.getJSONArray("response");
          int i = 0;
          Log.d("O_MY","length="+jsonArray.length());
          while (i<jsonArray.length()) {
            Log.d("O_MY","pos="+i);
            JSONObject company = jsonArray.getJSONObject(i);
            Log.d("O_MY","company="+company.getString("company"));

            Company Currentcompany = new Company(
                company.getString("company"),
                company.getString("profile"),
                company.getString("location"),
                company.getString("ctc"),
                company.getString("dateReg")
            );

            companies.add(i,Currentcompany);
            i++;
          }
          adapter.addAll(companies);


        } catch (JSONException e) {
          e.printStackTrace();
        }


      }
    }
}
