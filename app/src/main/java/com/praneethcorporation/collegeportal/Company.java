package com.praneethcorporation.collegeportal;

/**
 * Created by PRANAYKUMAR on 06-10-2017.
 */

public class Company {

  private String companyName;
  private String jobProfile;
  private String jobLocation;
  private String ctc;
  private String dtReg;

  public Company(String companyName,String jobProfile,String jobLocation,String ctc,String dtReg){
    this.companyName=companyName;
    this.jobProfile=jobProfile;
    this.jobLocation=jobLocation;
    this.ctc=ctc;
    this.dtReg=dtReg;
  }
  public String getCompanyName() {
    return companyName;
  }

  public String getJobProfile() {
    return jobProfile;
  }

  public String getJobLocation() {
    return jobLocation;
  }

  public String getCtc() {
    return ctc;
  }

  public String getDtReg() {
    return dtReg;
  }
}
