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
import java.util.List;

/**
 * Created by user on 10/7/2017.
 */

public class CurrentOpeningsAdapter extends ArrayAdapter<CurrentOpeningCompanies> implements View.OnClickListener {


    public CurrentOpeningsAdapter(@NonNull Context context, @NonNull ArrayList<CurrentOpeningCompanies> companies) {
        super(context, 0, companies);
    }

    @Override
    public void onClick(View v) {

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        CurrentOpeningCompanies currentOpeningCompanies = getItem(position);
        if(convertView==null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.currentopeningslistitem, parent, false);
        }



        TextView companyNameTxtView=(TextView)convertView.findViewById(R.id.companyName);
        TextView jobProfileTxtView=(TextView)convertView.findViewById(R.id.profile);
        TextView jobLocationTxtView=(TextView)convertView.findViewById(R.id.branchesAllowed);
        TextView ctcTxtView=(TextView)convertView.findViewById(R.id.ctc);
        TextView dtRegTxtView=(TextView)convertView.findViewById(R.id.deadline);

        companyNameTxtView.setText(currentOpeningCompanies.getCompanyName());
        jobProfileTxtView.setText(currentOpeningCompanies.getJobProfile());
        jobLocationTxtView.setText(currentOpeningCompanies.getBranchesAllowed());
        ctcTxtView.setText(currentOpeningCompanies.getCtc());
        dtRegTxtView.setText(currentOpeningCompanies.getDtReg());

        return convertView;
    }
}
