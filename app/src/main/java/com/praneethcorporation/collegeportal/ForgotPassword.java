package com.praneethcorporation.collegeportal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ForgotPassword extends AppCompatActivity {

    Spinner securityQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
   securityQuestion = (Spinner)findViewById(R.id.securityQuestion);
        ArrayAdapter securityAdapter = ArrayAdapter
                .createFromResource(this, R.array.securityQestions, R.layout.support_simple_spinner_dropdown_item);
        securityAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        securityQuestion.setAdapter(securityAdapter);
     //   securityQuestion.setSelection(securityAdapter.getPosition(info.gender), false);


    }
}
