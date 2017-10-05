package com.praneethcorporation.collegeportal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;

import java.util.ArrayList;
import java.util.List;

public class FinishedActivty extends AppCompatActivity {

    Button backToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_activty);
        backToLogin = (Button)findViewById(R.id.backtoLogin);
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        HorizontalStepView stepView = (HorizontalStepView)findViewById(R.id.step_view);
        List<StepBean> stepBeanList = new ArrayList<>();
        StepBean stepBean = new StepBean("Academic",1);
        StepBean stepBean1 = new StepBean("Personal",1);
        StepBean stepBean2 = new StepBean("Done",0);
        stepBeanList.add(stepBean);

        stepBeanList.add(stepBean1);

        stepBeanList.add(stepBean2);





        stepView
                .setStepViewTexts(stepBeanList)//
                .setTextSize(8)//set textSize
                .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#00796B"))//StepsViewIndicator
                .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#4DB6AC"))//StepsViewIndicator
                .setStepViewComplectedTextColor(Color.parseColor("#009688"))//StepsView text
                .setStepViewUnComplectedTextColor(Color.parseColor("#4DB6AC"))//StepsView text
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.success))//StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.unvisited))//StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.current));//StepsViewIndicator AttentionIcon


    }
}

