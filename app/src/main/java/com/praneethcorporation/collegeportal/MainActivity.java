package com.praneethcorporation.collegeportal;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.praneethcorporation.collegeportal.databinding.ActivityMainBinding;
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
import java.util.Objects;

public class
MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_main);


      binding=DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = this;

        binding.registration.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (binding.registration.length() == 7) {
              binding.registration.clearFocus();
              binding.pass.requestFocus();
              binding.pass.setCursorVisible(true);
            }
          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {

          }

          @Override
          public void afterTextChanged(Editable s) {

          }
        });

      binding.pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

      binding.loginBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

            BackgroundTask task = new BackgroundTask(context);
            task.execute("login", binding.registration.getText().toString(), binding.pass.getText().toString());
          }
        });
      }

    public class BackgroundTask extends AsyncTask<String, Void, String> {

        AlertDialog alertDialog;
        private Dialog loadingDialog;
        Context ctx;

        BackgroundTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            //alertDialog = new AlertDialog.Builder(ctx).create();
            //alertDialog.setTitle("Login Information....");
            loadingDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Loading...");
        }

        @Override
        protected String doInBackground(String... params) {
            String reg_url = "http://139.59.5.186/php/register.php";
            String login_url = "http://139.59.5.186/php/login.php";
            String method = params[0];
            if (method.equals("register")) {

                String user_name = params[1];
                String user_pass = params[2];
                try {
                    URL url = new URL(reg_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    //httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data =
                            URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8")
                                    + "&" +
                                    URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder
                                    .encode(user_pass, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();
                    InputStream IS = httpURLConnection.getInputStream();
                    IS.close();
                    //httpURLConnection.connect();
                    httpURLConnection.disconnect();
                    return "Registration Success...";
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (method.equals("login")) {
                String login_name = params[1];
                String login_pass = params[2];
                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(
                            new OutputStreamWriter(outputStream, "UTF-8"));
                    String data =
                            URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(login_name, "UTF-8")
                                    + "&" +
                                    URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder
                                    .encode(login_pass, "UTF-8");
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
            loadingDialog.dismiss();

            String s="failure";
            if (result.trim().equals(s)) {
                Snackbar.make(findViewById(R.id.main),"Login Failed...Please! Try Again", Snackbar.LENGTH_SHORT)
                        .show();



            } else {
                //alertDialog.setMessage(result);
                //alertDialog.show();
                //Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
                Snackbar.make(findViewById(R.id.main), result, Snackbar.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(getApplicationContext(),Home.class);
                intent.putExtra("reg_no",result.substring(24,32));
                Log.d("O_MY",result.substring(24,32));
              startActivity(intent);
            }


        }
    }
}