package com.praneethcorporation.collegeportal.InfoClasses;

/**
 * Created by PRANAYKUMAR on 26-09-2017.
 */

public class Internship {

  private String id;
  private String reg_no;
  private String internship;
  private String duration;
  private String description;
  private String company;

  public Internship(String id, String reg_no, String internship, String company, String duration,
      String description) {
    this.id = id;
    this.reg_no = reg_no;
    this.internship = internship;
    this.duration = duration;
    this.description = description;
    this.company = company;
  }

  public String getId() {
    return id;
  }

  public String getReg_no() {
    return reg_no;
  }

  public String getInternship() {
    return internship;
  }

  public String getDuration() {
    return duration;
  }

  public String getDescription() {
    return description;
  }

  public String getCompany() {
    return company;
  }
}
