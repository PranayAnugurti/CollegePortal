package com.praneethcorporation.collegeportal;

import static com.praneethcorporation.collegeportal.UserInfo.reg_no;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.praneethcorporation.collegeportal.Home.BackgroundTask;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 8/29/2017.
 */

public class Tab1 extends Fragment {
Button b1;
  String reg_no;
  UserInfo info;

  @BindView(R.id.tv_name)TextView nameView;
  @BindView(R.id.tv_category)TextView categoryView;
  @BindView(R.id.tv_branch)TextView branchView;
  @BindView(R.id.tv_renid)TextView regView;
  @BindView(R.id.tv_coursename)TextView courseView;
  @BindView(R.id.tv_dob)TextView dobView;
  @BindView(R.id.tv_email)TextView emailView;
  @BindView(R.id.tv_skypeId)TextView skypeView;
  @BindView(R.id.tv_linkedinId)TextView linkedinView;
  @BindView(R.id.tv_physicallyChalleneged)TextView phdView;
  @BindView(R.id.tv_gender)TextView genderView;
  @BindView(R.id.tv_resedentialstatus)TextView residentialView;
  @BindView(R.id.tv_guardian)TextView guardianView;
  @BindView(R.id.tv_presentAddress)TextView presentAddressView;
  @BindView(R.id.tv_permanentAddress)TextView permanentAddressView;
  @BindView(R.id.tv_MartialStatus)TextView maritalStatusView;
  @BindView(R.id.tv_state)TextView stateView;
  @BindView(R.id.tv_country)TextView countryView;
  private Unbinder unbinder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, container, false);
      unbinder=ButterKnife.bind(this, view);
        Context c = getActivity().getApplicationContext();

        Intent i=getActivity().getIntent();
        reg_no=i.getStringExtra("reg_no");
      BackgroundTask task=new BackgroundTask(c);
      task.execute();
        b1 = (Button)view.findViewById(R.id.editButton1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Tab1Form.class);
            startActivity(intent);
            }
        });

        return view;
    }
  public class BackgroundTask extends AsyncTask<Void, Void, String> {

    String json_url;
    Context ctx;


    BackgroundTask(Context ctx) {
      this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
      json_url="http://139.59.5.186/php/user_info.php";
      Log.d("O_MY",reg_no);
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
        String data = URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(reg_no, "UTF-8");


        bufferedWriter.write(data);
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(inputStream, "iso-8859-1"));
        StringBuilder stringBuilder=new StringBuilder();


        String JSON_STRING;
        while (( JSON_STRING= bufferedReader.readLine()) != null) {
          stringBuilder.append(JSON_STRING+"\n");
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

        Log.d("O_MY",result);

        JSONObject base_json=new JSONObject(result);
        Log.d("O_MY",base_json.toString());

        JSONObject a=base_json.getJSONObject("response");

        info=new UserInfo(a.getString("reg_no"),a.getString("name"),a.getString("user_pass"),a.getString("image"),
            a.getString("course"),a.getString("branch"),a.getString("dob"),
            a.getString("email"),a.getString("skype"),a.getString("linkedin"),a.getString("gender"),
            a.getString("category"),a.getString("phd"),a.getString("residential_status"),a.getString("guardian"),
            a.getString("present_address"),a.getString("permanent_address"),
            a.getString("marital_status"),a.getString("state"),a.getString("country"));
        Log.d("O_MY_PAPPI",a.getString("reg_no")+a.getString("email"));

        regView.setText(info.reg_no);
        nameView.setText(info.name);
        branchView.setText(info.branch);
        dobView.setText(info.dob);
        courseView.setText(info.course);
        emailView.setText(info.email);
        skypeView.setText(info.skype);
        linkedinView.setText(info.linkedin);
        genderView.setText(info.gender);
        presentAddressView.setText(info.present_address);
        permanentAddressView.setText(info.permanent_address);
        categoryView.setText(info.category);
        phdView.setText(info.phd);
        residentialView.setText(info.guardian);
        maritalStatusView.setText(info.marital_status);
        stateView.setText(info.state);
        countryView.setText(info.country);

      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
  }

}
