package com.praneethcorporation.collegeportal;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.praneethcorporation.collegeportal.InfoClasses.InterviewExperiencesInfo;
import com.praneethcorporation.collegeportal.InfoClasses.User;
import com.praneethcorporation.collegeportal.PlaceMentStatisticsPackage.PlaceMentStatistics;

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
import java.util.Calendar;

public class AddInterviewExperience extends AppCompatActivity {


    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    EditText date, company, description, profile;
    Button btn;
    ScrollView scrollView;
    private Calendar calendar;
    private int year, month, day;
    String companyString, descriptionString, profileString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_interview_experience);
        profile = (EditText) findViewById(R.id.profileEdTxt);
        date = (EditText) findViewById(R.id.dateEdTxt);
        scrollView = (ScrollView) findViewById(R.id.addInterviewExperinceScrollView);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        company = (EditText) findViewById(R.id.companyEdTxt);
        description = (EditText) findViewById(R.id.experienceEdTxt);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
        date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showDialog(999);
                }
                return false;
            }
        });
        btn = (Button) findViewById(R.id.saveBtn);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add InterView Experience");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                } else if (id == R.id.nav_currentOpening) {
                    Intent intent = new Intent(getApplicationContext(), CurrentOpenings.class);
                    startActivity(intent);
                } else if (id == R.id.nav_registeredCompanies) {
                    Intent intent = new Intent(getApplicationContext(), RegisterCompanies.class);
                    startActivity(intent);
                } else if (id == R.id.nav_addInterviewExperience) {
                    Intent intent = new Intent(getApplicationContext(), AddInterviewExperience.class);
                    startActivity(intent);
                } else if (id == R.id.nav_currentOpening) {
                    Intent intent = new Intent(getApplicationContext(), CurrentOpenings.class);
                    startActivity(intent);
                } else if (id == R.id.nav_registeredCompanies) {
                    Intent intent = new Intent(getApplicationContext(), RegisterCompanies.class);
                    startActivity(intent);
                } else if (id == R.id.nav_interviewExperinces) {
                    Intent intent = new Intent(getApplicationContext(), InterviewExperinces.class);
                    startActivity(intent);

                } else if (id == R.id.nav_place_stats) {
                    Intent intent = new Intent(getApplicationContext(), PlaceMentStatistics.class);
                    startActivity(intent);
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                companyString = company.getText().toString().trim();
                descriptionString = description.getText().toString().trim();
                profileString = profile.getText().toString().trim();


                if (!(companyString.isEmpty()) && !(descriptionString.isEmpty()) && !(profileString.isEmpty())) {

                    InterviewExperiencesInfo info = new InterviewExperiencesInfo(
                            UserInfo.reg_no,
                            company.getText().toString(),
                            UserInfo.name,
                            date.getText().toString(),
                            description.getText().toString()
                    );
                    //Log.d("O_MY",UserInfo.reg_no+company.getText().toString()+description.getText().toString()+date.getText().toString()+UserInfo.name);
                    AddInterviewAsyncTask task = new AddInterviewAsyncTask(getApplicationContext());
                    task.execute(info);

                } else {
                    onRestart();
                    Snackbar snackbar = Snackbar.make(scrollView, "Dont Leave Any fields Empty", BaseTransientBottomBar.LENGTH_LONG);
                    snackbar.show();
                    profile.setText(profileString);
                    description.setText(descriptionString);
                    company.setText(companyString);
                }
            }
        });


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
            User user = new User(getApplicationContext());
            user.remove();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.aboutdevelopers) {
            Intent intent = new Intent(getApplicationContext(), AboutDevelopers.class);
            startActivity(intent);

        }
        if (id == R.id.help) {
            Intent intent = new Intent(getApplicationContext(), Help.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        Log.d("O_MY", "" + day + "/" + month + "/" + year);
        date.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    public class AddInterviewAsyncTask extends AsyncTask<InterviewExperiencesInfo, Void, String> {
        String update_url;
        Context ctx;

        AddInterviewAsyncTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            update_url = "http://139.59.5.186/php/add_interview.php";
        }

        @Override
        protected String doInBackground(InterviewExperiencesInfo... params) {

            InterviewExperiencesInfo interviewExperiencesInfo = params[0];


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
                String data =
                        URLEncoder.encode("reg_no", "UTF-8") + "=" + URLEncoder.encode(interviewExperiencesInfo.getReg_no(), "UTF-8")
                                + "&" +
                                URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(interviewExperiencesInfo.getStudentName(), "UTF-8")
                                + "&" +
                                URLEncoder.encode("company", "UTF-8") + "=" + URLEncoder.encode(interviewExperiencesInfo.getCompanyName(), "UTF-8")
                                + "&" +
                                URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(interviewExperiencesInfo.getDate(), "UTF-8")
                                + "&" +
                                URLEncoder.encode("experience", "UTF-8") + "=" + URLEncoder.encode(interviewExperiencesInfo.getExperience(), "UTF-8");
                Log.d("O_MY", interviewExperiencesInfo.getReg_no() + interviewExperiencesInfo.getCompanyName() + interviewExperiencesInfo.getDate() + interviewExperiencesInfo.getExperience());
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
            if (s.contains("Interview details saved successfully!!")) {
                Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
