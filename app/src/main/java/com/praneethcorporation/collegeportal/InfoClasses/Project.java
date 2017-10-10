package com.praneethcorporation.collegeportal.InfoClasses;

import android.widget.ProgressBar;

/**
 * Created by PRANAYKUMAR on 26-09-2017.
 */

public class Project {

  private String id;
  private String project;
  private String reg_no;
  private String project_link;
  private String duration;
  private String description;

  public Project(String id, String reg_no, String project, String duration, String description,
      String project_link) {
    this.id = id;
    this.project = project;
    this.reg_no = reg_no;
    this.duration = duration;
    this.description = description;
    this.project_link = project_link;
  }

  public String getId() {
    return id;
  }

  public String getProject() {
    return project;
  }

  public String getDuration() {
    return duration;
  }

  public String getDescription() {
    return description;
  }

  public String getReg_no() {
    return reg_no;
  }

  public String getProject_link() {
    return project_link;
  }
}
