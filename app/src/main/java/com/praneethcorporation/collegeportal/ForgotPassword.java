package com.praneethcorporation.collegeportal;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.praneethcorporation.collegeportal.InfoClasses.Internship;
import com.praneethcorporation.collegeportal.InfoClasses.Project;
import com.squareup.picasso.Picasso;
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

public class ForgotPassword extends AppCompatActivity {

    Spinner securityQuestion;
  EditText security_ans;
  EditText registration;
  String ques,ans;
  String reg_no;
  Context context;
  Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
   securityQuestion = (Spinner)findViewById(R.id.securityQuestion);
   security_ans = (EditText) findViewById(R.id.securityQuestionAnswer);
   registration = (EditText) findViewById(R.id.registration);
   btn = (Button) findViewById(R.id.loginButton);
      context=this;
        ArrayAdapter securityAdapter = ArrayAdapter
                .createFromResource(this, R.array.securityQestions, R.layout.support_simple_spinner_dropdown_item);
        securityAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        securityQuestion.setAdapter(securityAdapter);
     //   securityQuestion.setSelection(securityAdapter.getPosition(info.gender), false);
      btn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          reg_no=registration.getText().toString();
          Log.d("O_MY",reg_no);
          new BackgroundTask(context).execute();
        }
      });
    }
  public class BackgroundTask extends AsyncTask<String, Void, String> {

    String json_url;
    Context ctx;

    BackgroundTask(Context ctx) {
      this.ctx = ctx.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
      json_url = "http://139.59.5.186/php/forgot_password.php";
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
      try {

        Log.d("O_MY", "Result=" + result);

        JSONObject base_json = new JSONObject(result);
        Log.d("O_MY", base_json.toString());

        JSONObject a = base_json.getJSONObject("response");

        ques=a.getString("pass_ques");
        ans=a.getString("pass_ans");
        Log.d("O_MY",ques+securityQuestion.getSelectedItem().toString()+"  "+ans+security_ans.getText().toString());


        if(ques.contains(securityQuestion.getSelectedItem().toString())&&ans.contains(security_ans.getText().toString())) {
          Intent intent = new Intent(ctx.getApplicationContext(), ChangePassword.class);
          intent.putExtra("reg_no", reg_no);
          Log.d("O_MY","qurs ans crct");
          intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          ctx.startActivity(intent);
        }else{
          Snackbar.make(findViewById(R.id.layout),"Security Question and Answer doesn't match",Snackbar.LENGTH_LONG).show();
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
  }
  }
