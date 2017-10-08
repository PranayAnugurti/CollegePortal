package com.praneethcorporation.collegeportal;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 10/7/2017.
 */

public class
InterviewExperiencesAdapter extends ArrayAdapter<InterviewExperiencesInfo> implements View.OnClickListener {
    public InterviewExperiencesAdapter(@NonNull Context context, @NonNull ArrayList<InterviewExperiencesInfo> experiences) {
        super(context, 0, experiences);
    }

    @Override
    public void onClick(View v) {

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        InterviewExperiencesInfo interviewExperiencesInfo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.interviewexperiencesingleitem, parent, false);
        }

        TextView companyName = (TextView) convertView.findViewById(R.id.companyName);
        TextView studentName = (TextView) convertView.findViewById(R.id.studentName);
        TextView date = (TextView) convertView.findViewById(R.id.Date);
        TextView description = (TextView) convertView.findViewById(R.id.Description);

        companyName.setText(interviewExperiencesInfo.getCompanyName());
        studentName.setText("-"
            + ""+interviewExperiencesInfo.getStudentName());
        date.setText(interviewExperiencesInfo.getDate());
        description.setText(interviewExperiencesInfo.getExperience());

        return convertView;
    }
}
