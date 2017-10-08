package com.praneethcorporation.collegeportal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class InterviewExperinces extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_experinces);

listView = (ListView)findViewById(R.id.interviewExperiencesList);

        ArrayList<InterviewExperiencesInfo> arrayList = new ArrayList<>();
        arrayList.add(new InterviewExperiencesInfo("Google","Praneeth","12/10/2017"));
        arrayList.add(new InterviewExperiencesInfo("Microsoft","Pranay","8/9/2017"));
        arrayList.add(new InterviewExperiencesInfo("Nodemon","Sumanth","12/3/2017"));

        arrayList.add(new InterviewExperiencesInfo("Nodemon","Sumanth","12/3/2017"));

        arrayList.add(new InterviewExperiencesInfo("Nodemon","Sumanth","12/3/2017"));

        arrayList.add(new InterviewExperiencesInfo("Nodemon","Sumanth","12/3/2017"));


        InterviewExperiencesAdapter interviewExperiencesAdapter = new InterviewExperiencesAdapter(getApplicationContext(),arrayList);

        listView.setAdapter(interviewExperiencesAdapter);

    }
}
