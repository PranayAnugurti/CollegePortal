package com.praneethcorporation.collegeportal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        arrayList.add(new CurrentOpeningCompanies("Veritas", "Tech analyst", "CSE,IT,ECE", "Rs 25000", "14th October"));

        arrayList.add(new CurrentOpeningCompanies("Goldman Sachs", "Tech analyst", "CSE,IT,ECE", "Rs 25000", "14th October"));

        arrayList.add(new CurrentOpeningCompanies("Morgan Stanley", "Tech analyst", "CSE,IT,ECE", "Rs 25000", "14th October"));

        CurrentOpeningsAdapter currentOpeningsAdapter = new CurrentOpeningsAdapter(this, arrayList);

        listView.setAdapter(currentOpeningsAdapter);
    }
}
