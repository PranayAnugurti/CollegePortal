package com.praneethcorporation.collegeportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.praneethcorporation.collegeportal.Adapters.CurrentOpeningsAdapter;
import com.praneethcorporation.collegeportal.InfoClasses.CurrentOpeningCompanies;
import com.praneethcorporation.collegeportal.PlaceMentStatisticsPackage.PlaceMentStatistics;

import java.util.ArrayList;

/**
 * Created by user on 8/31/2017.
 */

public class CurrentOpenings extends AppCompatActivity {


    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.currentopenings);
        listView = (ListView) findViewById(R.id.currentOpeningList);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add InterView Experience");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
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


        ArrayList<CurrentOpeningCompanies> arrayList = new ArrayList<>();
        arrayList.add(new CurrentOpeningCompanies("Praneeth Corporation", "Data analyst", "Branches Allowed:-All", "CTC:- Rs 10000000", "11th October"));

        arrayList.add(new CurrentOpeningCompanies("Goldman Sachs", "Tech analyst", "Branches Allowed:-CSE,IT,ECE", "CTC:- Rs 500000", "12th October"));

        arrayList.add(new CurrentOpeningCompanies("Morgan Stanley", "Networks Engineer", "Branches Allowed:-CSE,IT,ECE", "CTC:- Rs 750000", "13th October"));

        arrayList.add(new CurrentOpeningCompanies("Arsita", "SoftWare Developer", "Branches Allowed:-CSE,IT,ECE", "CTC:- Rs 450000", "14th October"));

        CurrentOpeningsAdapter currentOpeningsAdapter = new CurrentOpeningsAdapter(this, arrayList);

        listView.setAdapter(currentOpeningsAdapter);

    }


    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

}
