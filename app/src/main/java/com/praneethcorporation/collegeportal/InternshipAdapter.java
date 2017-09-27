package com.praneethcorporation.collegeportal;

import android.content.Context;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by PRANAYKUMAR on 26-09-2017.
 */

public class InternshipAdapter extends ArrayAdapter<Internship> implements View.OnClickListener {


  public InternshipAdapter(@NonNull Context context,ArrayList<Internship> internships) {
    super(context,0,internships);
  }

  @Override
  public void onClick(View v) {

  }

  @Nullable
  @Override
  public Internship getItem(int position) {
    return super.getItem(position);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    Internship internshipItem = getItem(position);
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext())
          .inflate(R.layout.internshipslistitem, parent, false);
    }
    TextView internshipTxtView = (TextView) convertView.findViewById(R.id.internshipTextView);
    TextView companyTxtView = (TextView) convertView.findViewById(R.id.companyTextView);
    TextView durationTxtView = (TextView) convertView.findViewById(R.id.durationTextView);
    TextView descriptionTxtView = (TextView) convertView.findViewById(R.id.descriptionTextView);


    internshipTxtView.setText(internshipItem.getInternship());
    companyTxtView.setText(internshipItem.getCompany());
    durationTxtView.setText(internshipItem.getDuration());
    descriptionTxtView.setText(internshipItem.getDescription());

    return convertView;
  }
}
