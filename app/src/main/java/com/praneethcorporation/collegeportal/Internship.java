package com.praneethcorporation.collegeportal;

/**
 * Created by PRANAYKUMAR on 26-09-2017.
 */

public class Internship {

private String reg_no;
private String internship;
private String duration;
private String description;
private String company;

  public Internship(String reg_no,String internship,String company,String duration,String description){
    this.reg_no=reg_no;
    this.internship=internship;
    this.duration=duration;
    this.description=description;
    this.company=company;
  }

  public String getReg_no(){
    return reg_no;
  }
  public String getInternship(){
    return internship;
  }
  public String getDuration(){
    return duration;
  }
  public String getDescription(){
    return description;
  }
  public String getCompany(){
    return company;
  }
}
