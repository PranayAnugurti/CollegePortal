package com.praneethcorporation.collegeportal.PlaceMentStatisticsPackage;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.praneethcorporation.collegeportal.LastYearStatistics;
import com.praneethcorporation.collegeportal.R;
import com.praneethcorporation.collegeportal.ViewPagerAdapter;

public class PlaceMentStatistics extends AppCompatActivity {
  TabLayout tabLayout;
  ViewPager viewPager;
  ViewPagerAdapter viewPagerAdapter;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_place_ment_statistics);
    tabLayout = (TabLayout)findViewById(R.id.tablayout);
    viewPager = (ViewPager)findViewById(R.id.viewPager);
    viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    viewPagerAdapter.addFragements(new LastYearStatistics(),"2017");
    viewPagerAdapter.addFragements(new PrevLastyearStatistcs(),"2016");
    viewPagerAdapter.addFragements(new PrevPrevLastYearStatitics(),"2015");
    viewPager.setAdapter(viewPagerAdapter);
    tabLayout.setupWithViewPager(viewPager);
  }
}