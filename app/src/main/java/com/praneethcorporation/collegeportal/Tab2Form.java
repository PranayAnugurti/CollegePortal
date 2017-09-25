package com.praneethcorporation.collegeportal;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.NumberPicker;

public class Tab2Form extends AppCompatActivity {
NumberPicker class10,class12;
  EditText school10,school12,board10,board12,per10,per12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab2form);

      //Getting data from intent
      Intent intent=getIntent();
      Bundle b=intent.getExtras();
      acad_info info=b.getParcelable("academic_details");

      school10=(EditText)findViewById(R.id.et_school10);
      school12=(EditText)findViewById(R.id.et_12schoolname);
      board10=(EditText)findViewById(R.id.et_10thboard);
      board12=(EditText)findViewById(R.id.et_12thboard);
      per10=(EditText)findViewById(R.id.et_10percentage);
      per12=(EditText)findViewById(R.id.et_datapicker);
      class10 = (NumberPicker)findViewById(R.id.clas10yearPicker);
      class10.setMaxValue(2050);
      class10.setMinValue(2010);
      class10.setValue(2013);
      class12 = (NumberPicker)findViewById(R.id.clas12yearPicker);
      class12.setMaxValue(2050);
      class12.setMinValue(2010);
      class12.setValue(2015);
      class10.setValue(parseInt(acad_info.year_10));
      class12.setValue(parseInt(acad_info.year_12));



      school10.setText(info.school_10);
      school12.setText(info.school_12);
      board12.setText(info.board_12);
      board10.setText(info.board_10);
      per10.setText(info.per_10);
      per12.setText(info.per_12);
    }
}

