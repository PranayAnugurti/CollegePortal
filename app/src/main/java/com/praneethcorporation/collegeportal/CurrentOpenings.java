package com.praneethcorporation.collegeportal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.praneethcorporation.collegeportal.Adapters.CurrentOpeningsAdapter;
import com.praneethcorporation.collegeportal.InfoClasses.CurrentOpeningCompanies;

import java.util.ArrayList;

/**
 * Created by user on 8/31/2017.
 */

public class CurrentOpenings extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.currentopenings);
        listView = (ListView) findViewById(R.id.currentOpeningList);

        ArrayList<CurrentOpeningCompanies> arrayList = new ArrayList<>();
        arrayList.add(new CurrentOpeningCompanies("Praneeth Corporation", "Data analyst", "Branches Allowed:-All", "CTC:- Rs 10000000", "11th October"));

        arrayList.add(new CurrentOpeningCompanies("Goldman Sachs", "Tech analyst", "Branches Allowed:-CSE,IT,ECE", "CTC:- Rs 500000", "12th October"));

        arrayList.add(new CurrentOpeningCompanies("Morgan Stanley", "Networks Engineer", "Branches Allowed:-CSE,IT,ECE", "CTC:- Rs 750000", "13th October"));

        arrayList.add(new CurrentOpeningCompanies("Arsita", "SoftWare Developer", "Branches Allowed:-CSE,IT,ECE", "CTC:- Rs 450000", "14th October"));

        CurrentOpeningsAdapter currentOpeningsAdapter = new CurrentOpeningsAdapter(this, arrayList);

        listView.setAdapter(currentOpeningsAdapter);
    }
}
