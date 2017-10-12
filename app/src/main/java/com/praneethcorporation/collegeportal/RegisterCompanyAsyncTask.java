package com.praneethcorporation.collegeportal;

import android.content.Context;

import android.os.AsyncTask;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.widget.Toast;
import com.praneethcorporation.collegeportal.InfoClasses.CurrentOpeningCompanies;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by PRANAYKUMAR on 11-10-2017.
 */

public class RegisterCompanyAsyncTask extends AsyncTask<CurrentOpeningCompanies,Void,String>{
  String register_url;
  Context ctx;

  public RegisterCompanyAsyncTask(Context ctx) {
    this.ctx = ctx;
  }
  @Override
  protected void onPreExecute() {
    register_url = "http://139.59.5.186/php/register_company.php";

  }

  @Override
  protected String doInBackground(CurrentOpeningCompanies... params) {
    CurrentOpeningCompanies company = params[0];
    URL url = null;
    try {
      url = new URL(register_url);

      HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

      httpURLConnection.setRequestMethod("POST");

      httpURLConnection.setDoOutput(true);
      httpURLConnection.setDoInput(true);
      OutputStream outputStream = httpURLConnection.getOutputStream();
      BufferedWriter bufferedWriter = new BufferedWriter(
          new OutputStreamWriter(outputStream, "UTF-8"));
      DateFormat dateFormatter = new SimpleDateFormat("EEEE,dd-MM-yyyy hh:mm:ss");
      dateFormatter.setLenient(false);
      Date today = new Date();
      String s = dateFormatter.format(today);
      String data =
          URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(company.getId(), "UTF-8")
              + "&" +
              URLEncoder.encode("company", "UTF-8") + "=" + URLEncoder.encode(company.getCompanyName(), "UTF-8")
              + "&" +
              URLEncoder.encode("profile", "UTF-8") + "=" + URLEncoder.encode(company.getJobProfile(), "UTF-8")
              + "&" +
              URLEncoder.encode("ctc", "UTF-8") + "=" + URLEncoder.encode(company.getCtc(), "UTF-8")
              + "&" +
              URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(UserInfo.reg_no, "UTF-8")
              + "&" +
              URLEncoder.encode("dateReg", "UTF-8") + "=" + URLEncoder.encode(s, "UTF-8")
              + "&" +
              URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(company.getLocation(), "UTF-8")
          ;
      bufferedWriter.write(data);
      bufferedWriter.flush();
      bufferedWriter.close();
      outputStream.close();
      InputStream inputStream = httpURLConnection.getInputStream();
      BufferedReader bufferedReader = new BufferedReader(
          new InputStreamReader(inputStream, "iso-8859-1"));
      String response = "";
      String line = "";
      while ((line = bufferedReader.readLine()) != null) {
        response += line;
      }
      bufferedReader.close();
      inputStream.close();
      httpURLConnection.disconnect();
      return response;
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();

    }
    return null;
  }

  @Override
  protected void onPostExecute(String s) {
    super.onPostExecute(s);
    if (s.contains("Registered successfully!!")) {
      Toast.makeText(ctx,s, Toast.LENGTH_LONG).show();
    } else {
      Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }
  }
}
