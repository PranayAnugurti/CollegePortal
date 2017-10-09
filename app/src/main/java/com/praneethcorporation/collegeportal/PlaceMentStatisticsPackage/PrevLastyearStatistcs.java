package com.praneethcorporation.collegeportal.PlaceMentStatisticsPackage;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.praneethcorporation.collegeportal.R;

import java.util.ArrayList;

public class PrevLastyearStatistcs extends Fragment implements OnChartValueSelectedListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_prev_lastyear_statistcs,container,false);

        PieChart pieChart = (PieChart) view.findViewById(R.id.piechart);
        PieChart pieChart1 = (PieChart) view.findViewById(R.id.piechart2);
        pieChart.setUsePercentValues(true);
        pieChart1.setUsePercentValues(true);

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        ArrayList<Entry> EligibileCandidates = new ArrayList<Entry>();
        EligibileCandidates.add(new Entry(29f, 0));//BIO
        EligibileCandidates.add(new Entry(155f, 1));//CSE
        EligibileCandidates.add(new Entry(35f, 2));//CHE
        EligibileCandidates.add(new Entry(57f, 3));//CIV
        EligibileCandidates.add(new Entry(78f, 4));//IT
        EligibileCandidates.add(new Entry(26f, 5));//PIE
        EligibileCandidates.add(new Entry(83f, 6));//MCA
        EligibileCandidates.add(new Entry(96f, 7));//ME
        EligibileCandidates.add(new Entry(47f, 8));//MBA
        EligibileCandidates.add(new Entry(346f, 9));//Mtech
        EligibileCandidates.add(new Entry(7f, 10));//MSW
        EligibileCandidates.add(new Entry(129f, 11));//ECE
        EligibileCandidates.add(new Entry(15f, 12));//M.Sc
        EligibileCandidates.add(new Entry(58f, 13));//EE


        ArrayList<Entry> Placed_Candidates = new ArrayList<Entry>();
        Placed_Candidates.add(new Entry(25f, 0));//BIO
        Placed_Candidates.add(new Entry(149f, 1));//CSE
        Placed_Candidates.add(new Entry(43f, 2));//CHE
        Placed_Candidates.add(new Entry(30f, 3));//CIV
        Placed_Candidates.add(new Entry(74f, 4));//IT
        Placed_Candidates.add(new Entry(23f, 5));//PIE
        Placed_Candidates.add(new Entry(83f, 6));//MCA
        Placed_Candidates.add(new Entry(96f, 7));//ME
        Placed_Candidates.add(new Entry(47f, 8));//MBA
        Placed_Candidates.add(new Entry(346f, 9));//Mtech
        Placed_Candidates.add(new Entry(0f, 10));//MSW
        Placed_Candidates.add(new Entry(124f, 11));//ECE
        Placed_Candidates.add(new Entry(3f, 12));//M.sc
        Placed_Candidates.add(new Entry(54f, 13));//EE


        PieDataSet EligibledataSet = new PieDataSet(EligibileCandidates, "Elgible Candidates");

        PieDataSet PlaceddataSet = new PieDataSet(Placed_Candidates, "Placed Candidates");

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("BIO");
        xVals.add("CSE");
        xVals.add("CHE");
        xVals.add("CIV");
        xVals.add("IT");
        xVals.add("PIE");
        xVals.add("MCA");
        xVals.add("ME");
        xVals.add("MBA");
        xVals.add("M.Tech All");
        xVals.add("MSW");
        xVals.add("ECE");
        xVals.add("M.Sc");
        xVals.add("EE");

        PieData Eligibledata = new PieData(xVals, EligibledataSet);


        PieData Placeddata = new PieData(xVals, PlaceddataSet);

        // In Percentage term
        Eligibledata.setValueFormatter(new PercentFormatter());
        Placeddata.setValueFormatter(new PercentFormatter());
        // Default value
        //data.setValueFormatter(new DefaultValueFormatter(0));
        pieChart.setData(Eligibledata);
        pieChart1.setData(Placeddata);
        pieChart.setDescription("This is Elgigble Candiadates Pie Chart ");


        pieChart1.setDescription("This is Placed Candiadates Pie Chart ");

        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(25f);
        pieChart.setHoleRadius(25f);

        pieChart1.setDrawHoleEnabled(true);
        pieChart1.setTransparentCircleRadius(25f);
        pieChart1.setHoleRadius(25f);

        PlaceddataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        EligibledataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        Eligibledata.setValueTextSize(13f);
        Placeddata.setValueTextSize(13f);
        Eligibledata.setValueTextColor(Color.DKGRAY);
        Placeddata.setValueTextColor(Color.DKGRAY);
        pieChart.setOnChartValueSelectedListener(this);
        pieChart1.setOnChartValueSelectedListener(this);

        pieChart.animateXY(1400, 1400);

        pieChart1.animateXY(1400, 1400);


        return view;
    }


    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }
}