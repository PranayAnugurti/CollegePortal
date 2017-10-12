package com.praneethcorporation.collegeportal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.praneethcorporation.collegeportal.InfoClasses.User;
import com.praneethcorporation.collegeportal.PlaceMentStatisticsPackage.PlaceMentStatistics;

public class
Profile extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    TabLayout tabLayout;
    ViewPager viewPager;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ViewPagerAdapter viewPagerAdapter;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
      int position = 0;
      Bundle extras = getIntent().getExtras();
      if(extras != null) {
        position = extras.getInt("viewpager_position");
      }
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
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

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragements(new Tab1(), "Personal");
        viewPagerAdapter.addFragements(new Tab2(), "Academic");
        viewPagerAdapter.addFragements(new Tab3(), "Projects");
        viewPagerAdapter.addFragements(new Tab4(), "Attachments");
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(position);
        tabLayout.setupWithViewPager(viewPager);
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

            AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
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
}
