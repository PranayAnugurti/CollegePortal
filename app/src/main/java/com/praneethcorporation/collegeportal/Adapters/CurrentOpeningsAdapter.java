package com.praneethcorporation.collegeportal.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;
import com.praneethcorporation.collegeportal.CurrentOpenings;
import com.praneethcorporation.collegeportal.InfoClasses.CurrentOpeningCompanies;
import com.praneethcorporation.collegeportal.RegisterCompanyAsyncTask;
import com.praneethcorporation.collegeportal.R;
import com.praneethcorporation.collegeportal.UserInfo;
import java.util.ArrayList;

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

        final CurrentOpeningCompanies currentOpeningCompanies = getItem(position);
        if(convertView==null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.currentopeningslistitem, parent, false);
        }



        TextView companyNameTxtView=(TextView)convertView.findViewById(R.id.companyName);
        TextView jobProfileTxtView=(TextView)convertView.findViewById(R.id.profile);
        TextView jobLocationTxtView=(TextView)convertView.findViewById(R.id.branchesAllowed);
        TextView ctcTxtView=(TextView)convertView.findViewById(R.id.ctc);
        TextView cpi=(TextView)convertView.findViewById(R.id.cpi);
        TextView dtRegTxtView=(TextView)convertView.findViewById(R.id.deadline);
        TextView location=(TextView)convertView.findViewById(R.id.location);
      Button btn=(Button) convertView.findViewById(R.id.saveBtn);

        companyNameTxtView.setText(currentOpeningCompanies.getCompanyName());
        jobProfileTxtView.setText(currentOpeningCompanies.getJobProfile());
        jobLocationTxtView.setText(currentOpeningCompanies.getBranchesAllowed());
        ctcTxtView.setText(currentOpeningCompanies.getCtc());
        cpi.setText(currentOpeningCompanies.getCpi());
        dtRegTxtView.setText(currentOpeningCompanies.getExamDate());
        location.setText(currentOpeningCompanies.getLocation());
        if(currentOpeningCompanies.getIsRegistered().equals("1")){
          btn.setText("Registered!");
          btn.setAlpha((float)0.5);
          btn.setEnabled(false);
          btn.setClickable(false);

        }
        else{
          btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              Log.d("O_MY",Float.valueOf(currentOpeningCompanies.getCpi())+"");
              String branch="All";
              if(UserInfo.branch.contains("Communication"))
                branch="ECE";
              else if(UserInfo.branch.contains("Computer"))
                branch="CSE";
              if(UserInfo.branch.contains("Information"))
                branch="IT";
              else if(UserInfo.branch.contains("Electrical"))
                branch="EE";
              else if(UserInfo.branch.contains("Mechanical"))
                branch="ME";
              else if(UserInfo.branch.contains("Production"))
                branch="PIE";
              else if(UserInfo.branch.contains("Civil"))
                branch="Civil";
              else if(UserInfo.branch.contains("Bio"))
                branch="ECE";
              else if(UserInfo.branch.contains("Chemical"))
                branch="CE";
              else if(UserInfo.branch.contains("MCA"))
                branch="MCA";

                Log.d("O_MY","branch check="+currentOpeningCompanies.getBranchesAllowed().contains(branch)+"");
              if((Float.valueOf(currentOpeningCompanies.getCpi())<=Float.valueOf(CurrentOpenings.user_cpi))
                  &&
                  (currentOpeningCompanies.getBranchesAllowed().contains(branch)||currentOpeningCompanies.getBranchesAllowed().contains("All"))
                  )
                    new RegisterCompanyAsyncTask(getContext()).execute(currentOpeningCompanies);
              else
                Toast.makeText(getContext(),"You are not eligible!!",Toast.LENGTH_LONG).show();

            }
          });
        }

        return convertView;
    }
}
