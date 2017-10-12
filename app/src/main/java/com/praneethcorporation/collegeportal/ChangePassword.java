package com.praneethcorporation.collegeportal;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.praneethcorporation.collegeportal.InfoClasses.Internship;
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

public class ChangePassword extends AppCompatActivity {
  EditText password,retypePassword;
  Button btn;
  String reg_no,pass;
  Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        password=(EditText)findViewById(R.id.et_password);
      context=this;
        retypePassword=(EditText)findViewById(R.id.et_retypepassword);
        btn=(Button) findViewById(R.id.doneBtn);
        reg_no=getIntent().getStringExtra("reg_no");
      btn.setOnClickListener(new OnClickListener() {


        @Override
        public void onClick(View v) {
          int isValid = 1;
          if (password.getText().toString().isEmpty()) {
           password.setError(getString(R.string.RegistartionNoCheck));
            isValid = 0;
          }
          if (retypePassword.getText().toString().isEmpty()) {
            retypePassword.setError(getString(R.string.MainPasswordCheck));
            isValid = 0;
          }
          if(!(retypePassword.getText().toString().equals(password.getText().toString()))){
            password.setError(getString(R.string.PasswordFieldsDidntMatchCheck));
            retypePassword.setError(getString(R.string.PasswordFieldsDidntMatchCheck));
            isValid=0;
          }
          if(isValid==1){
            pass=password.getText().toString();
            new BackgroundTask(context).execute();
          }
        }
      });
    }

  public class BackgroundTask extends AsyncTask<String, Void, String> {
    String update_url;
    Context ctx;

    BackgroundTask(Context ctx) {
      this.ctx = ctx.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
      update_url = "http://139.59.5.186/php/change_password.php";
    }

    @Override
    protected String doInBackground(String... params) {


      URL url = null;
      try {
        url = new URL(update_url);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");

        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        OutputStream outputStream = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(
            new OutputStreamWriter(outputStream, "UTF-8"));
        String data = URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(reg_no
            , "UTF-8") + "&" +
            URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(pass,"UTF-8");

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
      if (s.contains("Password updated succesfully!")) {
        Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ctx,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);

      } else {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
      }
    }
  }

}
