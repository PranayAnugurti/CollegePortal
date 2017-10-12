package com.praneethcorporation.collegeportal.InfoClasses;

/**
 * Created by user on 10/7/2017.
 */

public class CurrentOpeningCompanies {

  private String id;
  private String companyName;
  private String jobProfile;
  private String branchesAllowed;
  private String ctc;
  private String cpi;
  private String examDate;
  private String location;
  private String isRegistered;


  public CurrentOpeningCompanies(String id, String companyName, String jobProfile,
      String branchesAllowed,String cpi,  String ctc, String examDate, String isRegistered,
      String location) {
    this.id = id;
    this.companyName = companyName;
    this.jobProfile = jobProfile;
    this.branchesAllowed = branchesAllowed;
    this.ctc = ctc;
    this.cpi = cpi;
    this.examDate = examDate;
    this.location = location;
    this.isRegistered = isRegistered;
  }

  public String getId() {
    return id;
  }

  public String getCompanyName() {
    return companyName;
  }

  public String getJobProfile() {
    return jobProfile;
  }

  public String getBranchesAllowed() {
    return branchesAllowed;
  }

  public String getCtc() {
    return ctc;
  }

  public String getCpi() {
    return cpi;
  }

  public String getExamDate() {
    return examDate;
  }

  public String getLocation() {
    return location;
  }

  public String getIsRegistered() {
    return isRegistered;
  }
}
