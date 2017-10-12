package com.praneethcorporation.collegeportal;

import static com.praneethcorporation.collegeportal.UserInfo.reg_no;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.praneethcorporation.collegeportal.InfoClasses.acad_info;
import com.praneethcorporation.collegeportal.databinding.Tab2Binding;
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

public class Tab2 extends Fragment {
    acad_info info;
    Tab2Binding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      binding=DataBindingUtil.inflate(inflater,R.layout.tab2, container, false);
      Context c = getActivity().getApplicationContext();

        binding.editButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Tab2Form.class);
                intent.putExtra("academic_details", (android.os.Parcelable) info);
                startActivity(intent);
            }
        });

      Intent i = getActivity().getIntent();
      BackgroundTask task = new BackgroundTask(c);
      task.execute();
        return binding.getRoot();

    }
  public class BackgroundTask extends AsyncTask<Void, Void, String> {

    String json_url;
    Context ctx;
  BackgroundTask(Context ctx) {
    this.ctx = ctx;
  }

  @Override
  protected void onPreExecute() {
    binding.progressBar.setVisibility(View.VISIBLE);
    binding.ProgressLayout.setVisibility(View.INVISIBLE);
    json_url = "http://139.59.5.186/php/acad_info.php";
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
          URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(UserInfo.reg_no, "UTF-8");

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

      info=new acad_info(
          reg_no,
          a.getString("name"),
          a.getString("school_10"),
          a.getString("board_10"),
          a.getString("year_10"),
          a.getString("per_10"),
          a.getString("school_12"),
          a.getString("board_12"),
          a.getString("year_12"),
          a.getString("per_12"),
          a.getString("spi1"),
          a.getString("spi2"),
          a.getString("spi3"),
          a.getString("spi4"),
          a.getString("spi5"),
          a.getString("spi6"),
          a.getString("spi7"),
          a.getString("spi8"),
          a.getString("cpi")
      );
      binding.tvName.setText(info.name);
      binding.tvSchool10.setText(info.school_10);
      binding.tvBoard10.setText(info.board_10);
      binding.tvYear10.setText(info.year_10);
      binding.tvPer10.setText(info.per_10);
      binding.tvSchool12.setText(info.school_12);
      binding.tvBoard12.setText(info.board_12);
      binding.tvYear12.setText(info.year_12);
      binding.tvPer12.setText(info.per_12);
      binding.spi1.setText(info.spi1);
      binding.spi2.setText(info.spi2);
      binding.spi3.setText(info.spi3);
      binding.spi4.setText(info.spi4);
      binding.spi5.setText(info.spi5);
      binding.spi6.setText(info.spi6);
      binding.spi7.setText(info.spi7);
      binding.spi8.setText(info.spi8);
      binding.tvCpi.setText(info.cpi);



    } catch (JSONException e) {
      e.printStackTrace();
    }
    binding.progressBar.setVisibility(View.INVISIBLE);
    binding.ProgressLayout.setVisibility(View.VISIBLE);
  }
}
}
