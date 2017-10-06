package com.praneethcorporation.collegeportal;

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

import java.util.ArrayList;

public class LastYearStatistics extends Fragment implements OnChartValueSelectedListener {

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.activity_last_year_statistics, container, false);

    PieChart pieChart = (PieChart) view.findViewById(R.id.piechart);
    PieChart pieChart1 = (PieChart) view.findViewById(R.id.piechart2);
    pieChart.setUsePercentValues(true);
    pieChart1.setUsePercentValues(true);

    // IMPORTANT: In a PieChart, no values (Entry) should have the same
    // xIndex (even if from different DataSets), since no values can be
    // drawn above each other.
    ArrayList<Entry> yvalues = new ArrayList<Entry>();
    yvalues.add(new Entry(23f, 0));
    yvalues.add(new Entry(163f, 1));
    yvalues.add(new Entry(36f, 2));
    yvalues.add(new Entry(68f, 3));
    yvalues.add(new Entry(92f, 4));
    yvalues.add(new Entry(36f, 5));
    yvalues.add(new Entry(88f, 6));
    yvalues.add(new Entry(109f, 7));
    yvalues.add(new Entry(35f, 8));
    yvalues.add(new Entry(302f, 9));
    yvalues.add(new Entry(7f, 10));
    yvalues.add(new Entry(137f,11));
    yvalues.add(new Entry(14f, 12));
    yvalues.add(new Entry(66f, 13));

    PieDataSet dataSet = new PieDataSet(yvalues, "Elgible Candidates");

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

    PieData data = new PieData(xVals, dataSet);
    // In Percentage term
    data.setValueFormatter(new PercentFormatter());
    // Default value
    //data.setValueFormatter(new DefaultValueFormatter(0));
    pieChart.setData(data);
    pieChart1.setData(data);
    pieChart.setDescription("This is Elgigble Candiadates Pie Chart ");


    pieChart1.setDescription("This is Elgigble Candiadates Pie Chart ");

    pieChart.setDrawHoleEnabled(true);
    pieChart.setTransparentCircleRadius(25f);
    pieChart.setHoleRadius(25f);

    pieChart1.setDrawHoleEnabled(true);
    pieChart1.setTransparentCircleRadius(25f);
    pieChart1.setHoleRadius(25f);

    dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
    data.setValueTextSize(13f);
    data.setValueTextColor(Color.DKGRAY);
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