package com.praneethcorporation.collegeportal;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.mikelau.countrypickerx.Country;
import com.mikelau.countrypickerx.CountryPickerCallbacks;
import com.mikelau.countrypickerx.CountryPickerDialog;

/**
 * Created by user on 8/30/2017.
 */

public class Tab1Form extends AppCompatActivity {
    EditText dateofbirth;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    RadioGroup radiophGroup, radioresedentialgroup, radiomartialgroup;
    RadioButton ph, res, martial;
    Button save;
    Spinner genderSpinner,branchSpinner,courseSpinner,countrySpinner,stateSpinner;
    ArrayAdapter<CharSequence> genderAdapter,branchAdapter,courseAdapter,countryAdapter,stateAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1form);
        save = (Button) findViewById(R.id.saveform1);
        radiophGroup = (RadioGroup) findViewById(R.id.radiophGroup);
        radioresedentialgroup = (RadioGroup) findViewById(R.id.radioresedentialGroup);
        radiomartialgroup = (RadioGroup) findViewById(R.id.radiomartialGroup);
        genderSpinner = (Spinner)findViewById(R.id.genderspinner);
        genderAdapter = ArrayAdapter.createFromResource(this,R.array.gender,R.layout.support_simple_spinner_dropdown_item);
        genderAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), parent.getItemIdAtPosition(position)+" selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        branchSpinner = (Spinner)findViewById(R.id.branchspinner);
        branchAdapter = ArrayAdapter.createFromResource(this,R.array.branch,R.layout.support_simple_spinner_dropdown_item);
        branchAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        branchSpinner.setAdapter(branchAdapter);
        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), parent.getItemIdAtPosition(position)+" selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        courseSpinner = (Spinner)findViewById(R.id.coursespinner);
        courseAdapter = ArrayAdapter.createFromResource(this,R.array.course,R.layout.support_simple_spinner_dropdown_item);
        courseAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseAdapter);
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), parent.getItemIdAtPosition(position)+" selected", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        countrySpinner = (Spinner)findViewById(R.id.countryspinner);
        countryAdapter = ArrayAdapter.createFromResource(this,R.array.country,R.layout.support_simple_spinner_dropdown_item);
        courseAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), parent.getItemIdAtPosition(position)+" selected", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


         stateSpinner= (Spinner)findViewById(R.id.statespinner);
        stateAdapter = ArrayAdapter.createFromResource(this,R.array.states,R.layout.support_simple_spinner_dropdown_item);
        stateAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), parent.getItemIdAtPosition(position)+" selected", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int phselectedId = radiophGroup.getCheckedRadioButtonId();
                int resselectedId = (radioresedentialgroup).getCheckedRadioButtonId();
                int martialId = (radiomartialgroup).getCheckedRadioButtonId();
                ph = (RadioButton) findViewById(phselectedId);
                res = (RadioButton) findViewById(resselectedId);
                martial = (RadioButton) findViewById(martialId);
                Toast.makeText(getApplicationContext(), ph.getText() + "\n" + res.getText() + "\n" + martial.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        dateofbirth = (EditText) findViewById(R.id.et_datapicker);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
        dateofbirth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showDialog(999);
                    Toast.makeText(getApplicationContext(), "ca",
                            Toast.LENGTH_SHORT)
                            .show();
                }
                return false;
            }
        });

    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateofbirth.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }
}
