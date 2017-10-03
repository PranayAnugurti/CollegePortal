package com.praneethcorporation.collegeportal;

import static android.R.attr.resource;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by PRANAYKUMAR on 26-09-2017.
 */

public class ProjectAdapter extends ArrayAdapter<Project> {

  public ProjectAdapter(@NonNull Context context,
      ArrayList<Project> projetcs) {
    super(context,0,projetcs);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    final Project projectItem=getItem(position);

    if (convertView == null) {
      convertView = LayoutInflater.from(getContext())
          .inflate(R.layout.projectlistitem, parent, false);
    }
    TextView projectshipTxtView = (TextView) convertView.findViewById(R.id.project);
    TextView durationTxtView = (TextView) convertView.findViewById(R.id.durationView);
    TextView descriptionTxtView = (TextView) convertView.findViewById(R.id.descriptionView);
    Button viewBtn=(Button)convertView.findViewById(R.id.viewProject);
    final String url = projectItem.getProject_link();

    if(url==null || url.isEmpty()) {
      viewBtn.setVisibility(View.GONE);
    }
    else {
      viewBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {

          Intent i = new Intent(Intent.ACTION_VIEW);
          i.setData(Uri.parse(url));
          v.getContext().startActivity(i);
        }
      });

    }
    projectshipTxtView.setText(projectItem.getProject());
    durationTxtView.setText(projectItem.getDuration());
    descriptionTxtView.setText(projectItem.getDescription());
    return convertView;

  }



}
