package com.praneethcorporation.collegeportal;

import static android.R.attr.resource;

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
 * Created by PRANAYKUMAR on 06-10-2017.
 */

public class CompaniesAdapter extends ArrayAdapter<Company> implements View.OnClickListener{

  public CompaniesAdapter(@NonNull Context context,
      ArrayList<Company> companies) {
    super(context,0,companies);
  }

  @Override
  public void onClick(View v) {

  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    Company company=getItem(position);

    if(convertView==null) {
      convertView = LayoutInflater.from(getContext())
          .inflate(R.layout.registeredcompanieslistitem, parent, false);
    }

      TextView companyNameTxtView=(TextView)convertView.findViewById(R.id.companyTextView);
      TextView jobProfileTxtView=(TextView)convertView.findViewById(R.id.jobProfileTxtView);
      TextView jobLocationTxtView=(TextView)convertView.findViewById(R.id.jobLocationTxtView);
      TextView ctcTxtView=(TextView)convertView.findViewById(R.id.ctcTxtView);
      TextView dtRegTxtView=(TextView)convertView.findViewById(R.id.dateRegTxtView);

      companyNameTxtView.setText(company.getCompanyName());
      jobProfileTxtView.setText(company.getJobProfile());
      jobLocationTxtView.setText(company.getJobLocation());
      ctcTxtView.setText(company.getCtc());
      dtRegTxtView.setText(company.getDtReg());

    return convertView;
  }
}
