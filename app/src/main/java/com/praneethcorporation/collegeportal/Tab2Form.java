package com.praneethcorporation.collegeportal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;

public class Tab2Form extends AppCompatActivity {
NumberPicker class10,class12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab2form);

        class10 = (NumberPicker)findViewById(R.id.clas10yearPicker);
        class10.setMaxValue(2050);
        class10.setMinValue(2010);
        class10.setValue(2013);
        class12 = (NumberPicker)findViewById(R.id.clas12yearPicker);
        class12.setMaxValue(2050);
        class12.setMinValue(2010);
        class12.setValue(2015);
    }
}
