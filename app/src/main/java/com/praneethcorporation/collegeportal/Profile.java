package com.praneethcorporation.collegeportal;

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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
navigationView = (NavigationView)findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                    if (id == R.id.nav_currentOpening) {
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

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragements(new Tab1(), "Personal");
        viewPagerAdapter.addFragements(new Tab2(), "Academic");
        viewPagerAdapter.addFragements(new Tab3(), "Projects");
        viewPagerAdapter.addFragements(new Tab4(), "Attachments");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
}
