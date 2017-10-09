package com.praneethcorporation.collegeportal.InfoClasses;

/**
 * Created by user on 10/7/2017.
 */

public class CurrentOpeningCompanies {


    private String companyName;
    private String jobProfile;
    private String branchesAllowed;
    private String ctc;
    private String dtReg;


    public CurrentOpeningCompanies(String companyName,String jobProfile,String branchesAllowed,String ctc,String dtReg){
        this.companyName=companyName;
        this.jobProfile=jobProfile;
        this.branchesAllowed=branchesAllowed;
        this.ctc=ctc;
        this.dtReg=dtReg;
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

    public String getDtReg() {
        return dtReg;
    }

}
