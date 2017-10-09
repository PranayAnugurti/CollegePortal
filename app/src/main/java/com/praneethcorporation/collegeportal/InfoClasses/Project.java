package com.praneethcorporation.collegeportal.InfoClasses;

import android.widget.ProgressBar;

/**
 * Created by PRANAYKUMAR on 26-09-2017.
 */

public class Project {

  private String project;
  private String reg_no;
  private String project_link;
  private String duration;
  private String description;

  public Project(String reg_no,String project,String duration,String description,String project_link){
    this.project=project;
    this.reg_no=reg_no;
    this.duration=duration;
    this.description=description;
    this.project_link=project_link;
  }

  public String getProject(){
    return project;
  }
  public String getDuration(){
    return duration;
  }
  public String getDescription(){
    return description;
  }
public String getReg_no(){
  return reg_no;
}
public String getProject_link(){
  return project_link;
}
}
