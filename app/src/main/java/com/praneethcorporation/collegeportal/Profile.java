package com.praneethcorporation.collegeportal;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class
Profile extends AppCompatActivity {

  TabLayout tabLayout;
  ViewPager viewPager;
  ViewPagerAdapter viewPagerAdapter;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
  tabLayout = (TabLayout)findViewById(R.id.tablayout);
      viewPager = (ViewPager)findViewById(R.id.viewPager);
      viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
      viewPagerAdapter.addFragements(new Tab1(),"Personal");
      viewPagerAdapter.addFragements(new Tab2(),"Academic");
      viewPagerAdapter.addFragements(new Tab3(),"Projects");
      viewPagerAdapter.addFragements(new Tab4(),"Attachments");
viewPager.setAdapter(viewPagerAdapter);
      tabLayout.setupWithViewPager(viewPager);
  }
}
