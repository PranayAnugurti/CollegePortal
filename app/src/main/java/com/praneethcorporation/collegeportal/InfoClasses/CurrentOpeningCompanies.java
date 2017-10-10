package com.praneethcorporation.collegeportal.InfoClasses;

/**
 * Created by user on 10/7/2017.
 */

public class CurrentOpeningCompanies {


    private String companyName;
    private String jobProfile;
    private String branchesAllowed;
    private String ctc;
    private String examDate;
    private String location;
    private String isRegistered;


    public CurrentOpeningCompanies(String companyName,String jobProfile,String branchesAllowed,String ctc,String examDate,String isRegistered,String location){
        this.companyName=companyName;
        this.jobProfile=jobProfile;
        this.branchesAllowed=branchesAllowed;
        this.ctc=ctc;
        this.examDate=examDate;
        this.location=location;
        this.isRegistered=isRegistered;
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

    public String getExamDate() {
        return examDate;
    }

  public String getLocation() {
    return location;
  }

  public String getIsRegistered(){
    return isRegistered;
  }
}
