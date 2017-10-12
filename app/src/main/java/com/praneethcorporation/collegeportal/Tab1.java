package com.praneethcorporation.collegeportal;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.praneethcorporation.collegeportal.databinding.Tab1Binding;

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
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 8/29/2017.
 */

public class Tab1 extends Fragment {

  ProgressBar progressBar;
  Tab1Binding b;
  String reg_no;
  UserInfo info;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      final Bundle savedInstanceState) {

    b= DataBindingUtil.inflate(inflater,R.layout.tab1, container, false);
    Context c = getActivity().getApplicationContext();
    Intent i = getActivity().getIntent();
    reg_no = i.getStringExtra("reg_no");
    BackgroundTask task = new BackgroundTask(c);
    task.execute();

    b.editButton1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getContext(), Tab1Form.class);
        intent.putExtra("personal_details", (android.os.Parcelable) info);
        startActivity(intent);
      }
    });

    return b.getRoot();
  }

  public class BackgroundTask extends AsyncTask<Void, Void, String> {

    String json_url;
    Context ctx;


    BackgroundTask(Context ctx) {
      this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
      b.progressBar.setVisibility(View.VISIBLE);
      json_url = "http://139.59.5.186/php/user_info.php";
      Log.d("O_MY", reg_no);
    }

    @Override
    protected String doInBackground(Void... params) {

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
    protected void onProgressUpdate(Void... values) {
      super.onProgressUpdate(values);
    }

    @RequiresApi(api = VERSION_CODES.KITKAT)
    @Override
    protected void onPostExecute(String result) {


      try {

        Log.d("O_MY", result);

        JSONObject base_json = new JSONObject(result);
        Log.d("O_MY", base_json.toString());

        JSONObject a = base_json.getJSONObject("response");

        info = new UserInfo(a.getString("reg_no"), a.getString("name"), a.getString("user_pass"),
            a.getString("image"),
                a.getString("imageServerLink"),
            a.getString("pdfServerLink"),
            a.getString("course"), a.getString("branch"), a.getString("dob"),
            a.getString("email"), a.getString("skype"), a.getString("linkedin"),
            a.getString("gender"),
            a.getString("category"), a.getString("phd"), a.getString("residential_status"),
            a.getString("guardian"),
            a.getString("present_address"), a.getString("permanent_address"),
            a.getString("marital_status"), a.getString("state"), a.getString("country"));
        Log.d("O_MY_PAPPI", a.getString("reg_no") + a.getString("email"));

        b.tvRenid.setText(info.reg_no);
        b.tvName.setText(info.name);
        b.tvBranch.setText(info.branch);
        b.tvDob.setText(info.dob);
        b.tvCoursename.setText(info.course);
        b.tvEmail.setText(info.email);
        b.tvSkypeId.setText(info.skype);
        b.tvLinkedinId.setText(info.linkedin);
        b.tvGender.setText(info.gender);
        b.tvPresentAddress.setText(info.present_address);
        b.tvPermanentAddress.setText(info.permanent_address);
        b.tvCategory.setText(info.category);
        b.tvGuardian.setText(info.guardian);
        b.tvPhysicallyChalleneged.setText(info.phd);
        b.tvResedentialstatus.setText(info.residential_status);
        b.tvMartialStatus.setText(info.marital_status);
        b.tvState.setText(info.state);
        b.tvCountry.setText(info.country);

      } catch (JSONException e) {
        e.printStackTrace();
      }
    b.progressBar.setVisibility(View.INVISIBLE);
      b.ProgressLayout.setVisibility(View.VISIBLE);
    }
  }

}
