package com.praneethcorporation.collegeportal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.praneethcorporation.collegeportal.InfoClasses.User;
import com.praneethcorporation.collegeportal.PlaceMentStatisticsPackage.PlaceMentStatistics;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

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

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String reg_no;
    UserInfo info;
    CircleImageView imageView;
    boolean isCon = false;
    TextView nameView;
    TextView streamView;
    TextView branchView;
    TextView regView;
    Button viewProfileButton;
    String JSON_STRING;
    ProgressBar loadingIndicator;

    TextView drawerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Profile Activity access
//Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE);
        //Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        //If there is a network connection,fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            isCon = true;
            BackgroundTask task = new BackgroundTask(this);
            task.execute();
            imageView = (CircleImageView) findViewById(R.id.profilePicImageView);
            nameView = (TextView) findViewById(R.id.nameTextView);
            streamView = (TextView) findViewById(R.id.streamTextView);
            branchView = (TextView) findViewById(R.id.branchTextView);
            regView = (TextView) findViewById(R.id.regTextView);
            viewProfileButton = (Button) findViewById(R.id.viewProfileButton);
            loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);

            viewProfileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Profile.class);
                    intent.putExtra("reg_no", reg_no);
                    intent.putExtra("image", info.image_server_link);
                    intent.putExtra("pdf", info.pdf_server_link);
                    startActivity(intent);
                }
            });
        } else {
            Snackbar.make(findViewById(R.id.drawer_layout), "No Internet Connection!", Snackbar.LENGTH_LONG).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logOut) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
// Add the buttons
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    User user = new User(getApplicationContext());
                    user.remove();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    // User clicked OK button
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    dialog.dismiss();
                }
            });

// Create the AlertDialog
            builder.setMessage("Do you really want to Log Out ?");

            AlertDialog dialog = builder.create();
            dialog.show();


        }
        if (id == R.id.aboutdevelopers) {
            Intent intent = new Intent(Home.this, AboutDevelopers.class);
            startActivity(intent);

        }
        if (id == R.id.help) {
            Intent intent = new Intent(getApplicationContext(), Help.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (isCon) {
            if (id == R.id.nav_currentOpening) {
                Intent intent = new Intent(Home.this, CurrentOpenings.class);
                startActivity(intent);
            } else if (id == R.id.nav_registeredCompanies) {
                Intent intent = new Intent(Home.this, RegisterCompanies.class);
                startActivity(intent);
            } else if (id == R.id.nav_addInterviewExperience) {
                Intent intent = new Intent(Home.this, AddInterviewExperience.class);
                startActivity(intent);
            } else if (id == R.id.nav_currentOpening) {
                Intent intent = new Intent(Home.this, CurrentOpenings.class);
                startActivity(intent);
            } else if (id == R.id.nav_registeredCompanies) {
                Intent intent = new Intent(Home.this, RegisterCompanies.class);
                startActivity(intent);
            } else if (id == R.id.nav_interviewExperinces) {
                Intent intent = new Intent(Home.this, InterviewExperinces.class);
                startActivity(intent);

            } else if (id == R.id.nav_place_stats) {
                Intent intent = new Intent(getApplicationContext(), PlaceMentStatistics.class);
                startActivity(intent);
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public class BackgroundTask extends AsyncTask<Void, Void, String> {

        String json_url;
        Context ctx;


        BackgroundTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            json_url = "http://139.59.5.186/php/user_info.php";
            Intent intent = getIntent();
            reg_no = intent.getStringExtra("reg_no");

            if (reg_no == null) {
                reg_no = UserInfo.reg_no;
            }
            //     Log.d("O_MY", reg_no);
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

                String response = "";
                String line = "";
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
                        a.getString("image"), a.getString("imageServerLink"), a.getString("pdfServerLink"), a.getString("course"), a.getString("branch"), a.getString("dob"),
                        a.getString("email"), a.getString("skype"), a.getString("linkedin"),
                        a.getString("gender"),
                        a.getString("category"), a.getString("phd"), a.getString("residential_status"),
                        a.getString("guardian"),
                        a.getString("present_address"), a.getString("permanent_address"),
                        a.getString("marital_status"), a.getString("state"), a.getString("country")
                ,"","");
                Log.d("O_MY_PAPPI", a.getString("reg_no") + a.getString("email"));
                String s = info.image_url;

                //you have to get the part of the link 0B9nFwumYtUw9Q05WNlhlM2lqNzQ
                String[] p = s.split("/");
                //Create the new image link
                //String imageLink = "https://drive.google.com/uc?export=download&id=" + p[5];
                String imageLink = info.image_server_link;
                Log.d("O_MY", imageLink);

                Picasso.with(Home.this).load(imageLink).into(imageView, new com.squareup.picasso.Callback() {

                    @Override
                    public void onSuccess() {
                        loadingIndicator.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });

                regView.setText(info.reg_no);
                streamView.setText(info.course);
                branchView.setText(info.branch);
                nameView.setText(info.name);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}

